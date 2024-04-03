package mx.com.intx.security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import mx.com.intx.domain.Profile;
import mx.com.intx.domain.ProfileSection;
import mx.com.intx.domain.Section;
import mx.com.intx.domain.User;
import mx.com.intx.responses.ResponseWS;
import mx.com.intx.utils.LoggerIntx;

/**
 * Clase encargada de crear y validar un token interno del sistema, el cual se
 * utiliza para dar acceso a las tareas del WebService
 * 
 * @author INTX
 *
 */
public class JWTSecurity {

	/*********** Attributes ************/
	private final static String KEY = "/5OX/9Vf8GpL9r8vOzG8WKbYBEmP6omXoaLKoK6I1szKfSwHc6Txr3FKJ34l5DPUefn4O/Ionrs=";

	public final static String CLAIM_USER = "user";
	public final static String CLAIM_USER_ID = "user_id";
	public final static String CLAIM_NAME = "name";
	public final static String CLAIM_PROFILE = "profile_id";
	private final static Long EXPIRATION_TIME = 300L; // segundos

	/*********** Methods ************/
	/**
	 * Método principal de generación de Token, se debe ejecutar ya que se validaron
	 * las credenciales del usuario
	 * 
	 * @param user Objeto de usuario al que se le relacionará el Token
	 * @return
	 */
	public Map<String, Object> createJWT(User user, Long expiration) {
		// Inicializar mapa de respuesta que tendra el token o el error
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// Establecer el estatus del login
		resultMap.put("status_login", user != null ? "1" : "0");
		// Cadena de texto con la descripcion del loggeo
		String statusMessage = "Success";
		// validamos el tipo de respuesta que obtuvimos en base a la validacion del core
		if (user == null) {
			statusMessage = "Error de credenciales";
		}
		resultMap.put("status_login_message", statusMessage);

		// En caso de no ser un usuario valido ya no se genera el token
		if (user == null)
			return resultMap;

		// Identificador del usuario que este token representa
		String subject = user.getUsername();
		// Extablecer propiedades
		Map<String, Object> claimsProps = new HashMap<>();
		claimsProps.put(CLAIM_USER, user.getUsername());
		claimsProps.put(CLAIM_USER_ID, user.getIdUser());
		claimsProps.put(CLAIM_PROFILE, user.getProfile().getIdProfile());

		// Establecer tiempo máximo de sesión
		long expirationTime = (expiration != null ? expiration : EXPIRATION_TIME) * 1000;
		// Establecer fecha de inicio del token
		long nowMillis = System.currentTimeMillis();
		// Generar token
		String token = generateToken(subject, nowMillis, expirationTime, claimsProps);

		System.out.println("Token: " + token);

		// Almacenar token en el mapa
		resultMap.put("token", token);
		resultMap.put("expires_in", nowMillis + expirationTime);
		generateProfilePermissions(resultMap, user.getProfile());

		return resultMap;
	}

	/**
	 * Método que inserta en el mapa las secciones del perfil del usuario que se
	 * esta autenticando
	 * 
	 * @param resultMap ResultMap donde se insertaran las secciones
	 * @param profile   Objeto perfil de donde se obtendra las secciones
	 */
	private void generateProfilePermissions(Map<String, Object> resultMap, Profile profile) {
		if (resultMap == null || profile == null || profile.getProfileSections() == null
				|| profile.getProfileSections().size() == 0)
			return;

		List<Section> sections = new ArrayList<Section>();
		for (ProfileSection profileSection : profile.getProfileSections())
			sections.add(profileSection.getProfileSectionId().getSection());
		resultMap.put("sections", sections);
	}

	/**
	 * Método que genera el token con base en los valores recibidos
	 * 
	 * @param subject        Identificador del token
	 * @param nowMillis      Tiempo de inicio del token
	 * @param expirationTime Tiempo de expiración del token
	 * @param claimsProps    Propiedades del Token
	 * @return
	 */
	private static String generateToken(String subject, long nowMillis, long expirationTime,
			Map<String, Object> claimsProps) {
		// Algoritmo de encriptación del token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		// Crear objeto de fecha de inicio
		Date now = new Date(nowMillis);

		// ApiKey
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY);
		Key signingKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

		// Colocar propiedades generales del token
		JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString()).setIssuedAt(now).setSubject(subject)
				.setIssuer("InnovatixControl").signWith(signingKey, signatureAlgorithm);

		// Establecer el tiempo de finalización del token, se obtiene de sumar el tiempo
		// de inicio y el fin
		long expMillis = nowMillis + expirationTime;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);

		// En caso de recibir las propiedades (claims) se establecen
		if (claimsProps != null)
			builder.addClaims(claimsProps);

		// Construir el JWT y serializazor para comcompactar, URL-safe
		return builder.compact();
	}

	/**
	 * Metodo que validara la peticion, primero por el token y despues por la acción
	 * (idSection) a la que quiere hacer referencia en caso de recibirla
	 * 
	 * @param token
	 * @param idSection
	 * @param response
	 * @return
	 */
	public boolean validateRequest(String token, Long idSection, ResponseWS response) {
		token = token.replace("Bearer ", "");

		// Si el token no es válido o la sección no se recibio regresar false
		if (!validateTokenJWT(token, response) || idSection == null)
			return false;

		// Obtenemos el id del perfil
		Claims claims = decodeJWT(token);

		// Si no existe el valor en el token regresar false
		if (claims.get(CLAIM_PROFILE) == null)
			return false;

		Long.parseLong(claims.get(CLAIM_PROFILE).toString());

		// TEMPORAL
		Boolean success = true;

		// System.out.println("success: " + success);
		return success;
	}

	/**
	 * Metodo que validara la peticion, solo el token
	 * 
	 * @param token
	 * @param idSection
	 * @param response
	 * @return
	 */
	public boolean validateRequest(String token, ResponseWS response) {
		token = token.replace("Bearer ", "");

		// Si el token no es válido o la sección no se recibio regresar false
		if (!validateTokenJWT(token, response))
			return false;

		return true;
	}

	/**
	 * Método que valida el token de acuerdo a las excepciones del JWT
	 * 
	 * @param token
	 * @return true en caso de que el token siga siendo valido | false en caso
	 *         contrario
	 */
	private static boolean validateTokenJWT(String token, ResponseWS response) {
		Map<String, Object> mapValidation = null;
		try {
			// Se intenta parsear el token, en caso de no poderse, el token es invalido
			decodeJWT(token);
		} catch (Exception e) {
			// En caso de error, quiere decir que el token no es valido y regresamos el
			// error
			mapValidation = new HashMap<>();
			mapValidation.put("error_authorization", "Invalid token");
			mapValidation.put("error_authorization_message", e.getMessage());
			response.getErrors().putAll(mapValidation);
			return false;
		}

		return true;
	}

	/**
	 * Método que decodifica el token
	 * 
	 * @param jwt Token
	 * @return
	 */
	private static Claims decodeJWT(String jwt) {
		// Esta línea regresará una exception si no es un válido JWS
		Claims claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(KEY)).build()
				.parseClaimsJws(jwt).getBody();
		return claims;
	}

	/**
	 * Método que se encarga de regenerar un token a partir de uno anterior, Esto
	 * para que la sessión no expire mientras el usuario esta ocupando el sistema
	 * 
	 * @param oldToken Viejo token
	 * @return
	 */
	public Object getPropertyToken(String token, String property) {
		Claims claim = null;
		try {
			token = token.replace("Bearer ", "");
			claim = decodeJWT(token);
			return claim.get(property);
		} catch (Exception e) {
			LoggerIntx.printError("Error al desencriptar el token", e);
			return null;
		}
	}

	/**
	 * Método que se encarga de regenerar un token a partir de uno anterior, Esto
	 * para que la sessión no expire mientras el usuario esta ocupando el sistema
	 * 
	 * @param oldToken Viejo token
	 * @return
	 */
	public Map<String, Object> refreshToken(String oldToken, Long expiration) {
		// map de respuesta que tendra el token o el error
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// mapa con las propiedades claims para la creacion del token
		Map<String, Object> claimsProps = new HashMap<>();
		Claims claim = null;
		try {
			claim = decodeJWT(oldToken);

			claimsProps.put(CLAIM_USER, claim.get(CLAIM_USER));
			claimsProps.put(CLAIM_USER_ID, claim.get(CLAIM_USER_ID));
			claimsProps.put(CLAIM_NAME, claim.get(CLAIM_NAME));
			claimsProps.put(CLAIM_PROFILE, claim.get(CLAIM_PROFILE));
		} catch (Exception e) {
			// en caso de error, quiere decir que el token no es valido y regresamos el
			// error
			resultMap = new HashMap<>();
			resultMap.put("error_authorization", "Invalid token");
			resultMap.put("error_authorization_message", e.getMessage());
			return resultMap;
		}
		// Modificar tiempos de expiración
		if (claim != null) {
			long nowMillis = System.currentTimeMillis();
			// Establecer tiempo máximo de sesión
			Long expirationTime = (expiration != null ? expiration : EXPIRATION_TIME) * 1000;

			String token = generateToken(claim.getSubject(), nowMillis, expirationTime, claimsProps);
			resultMap.put("token", token);
			resultMap.put("expires_in", nowMillis + expirationTime);
		}
		return resultMap;
	}

}
