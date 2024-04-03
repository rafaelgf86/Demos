package mx.com.intx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletContext;
import mx.com.innovatix.encrypt.service.EncrypterCertificatesCodec;
import mx.com.intx.domain.LogType;
import mx.com.intx.domain.ProfileSection;
import mx.com.intx.domain.Setting;
import mx.com.intx.domain.User;
import mx.com.intx.entities.TokenData;
import mx.com.intx.exceptions.IntxException;
import mx.com.intx.responses.ResponseWS;
import mx.com.intx.security.JWTSecurity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;

/**
 * Servicio encargado de validar el usuario y sus permisos
 * 
 * @author INTX
 *
 */
@Service
public class JWTSecurityService extends Manager implements IJWTSecurityManager {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserManager userServ;

	@Autowired
	private IProfileManager profileServ;

	@Autowired
	private ISettingManager settingService;

	@Autowired
	private ServletContext servletContext;

	@Override
	public Map<String, Object> createJWT(String username, String password) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// Creamos el objeto que nos permitirá crear el token
			JWTSecurity jwtSecurity = new JWTSecurity();

			// Verificamos que existe un usuario asociado al usuario y password recibidos
			User user = userServ.getByUsername(username);

			// Si el usuario no se encontró finalizar creando un token vacio
			if (user == null)
				return jwtSecurity.createJWT(user, null);

			// Si el usuario existe hay que validar que el password desencriptado sea el
			// mismo que el recibido
			String passwordDesen = null;
			try {
				passwordDesen = this.desencrypt(user.getPassword());
			} catch (BadPaddingException e) {
				throw new IntxException(2000, "There is a problem with the associated certificates", e);
			}

			// Si no son los mismos finalizar con error
			if (!password.equals(passwordDesen))
				return jwtSecurity.createJWT(null, null);

			// Obtener los permisos
			List<ProfileSection> profileSections = this.profileServ
					.getProfileSections(user.getProfile().getIdProfile());
			// Asignamos al perfil que esta en el objeto usuario
			user.getProfile().setProfileSections(profileSections);

			// Obtener el tiempo de session
			Setting setting = this.settingService.getBasic();

			// Generar token y mapa de resultados
			resultMap = jwtSecurity.createJWT(user, (setting != null ? setting.getSessionTime() : null));

			// Guardar acceso a la base de datos
			super.logManager.addLog(LogType.INGRESO_SISTEMA, username, getClass().getCanonicalName(), username, null);

			// Regresar respuesta del servicio que recibe el objeto usuario
			// return resultMap;
		} catch (Exception exception) {
			// Establecer el estatus del login
			resultMap.put("status_login", "0");
			resultMap.put("status_login_message", exception.getMessage());
			super.logManager.addErrorLog(LogType.SESSION, "No se pudo crear el token", getClass().getCanonicalName(),
					exception, null);
			// throw exception;
		}
		return resultMap;
	}

	@Override
	public String getUsername(String token) {
		// Creamos el objeto que nos permitirá crear el token
		JWTSecurity jwtSecurity = new JWTSecurity();
		Object username = jwtSecurity.getPropertyToken(token, JWTSecurity.CLAIM_USER);
		return username + "";
	}

	@Override
	public Long getIdUser(String token) {
		// Creamos el objeto que nos permitirá crear el token
		JWTSecurity jwtSecurity = new JWTSecurity();
		Object id = jwtSecurity.getPropertyToken(token, JWTSecurity.CLAIM_USER_ID);
		return id != null ? Long.parseLong(id + "") : null;
	}

	@Override
	public boolean validateRequest(String token, ResponseWS response) {
		// Creamos el objeto que nos permitirá crear el token
		JWTSecurity jwtSecurity = new JWTSecurity();
		// Regresar respuesta del servicio que recibe el objeto usuario
		return jwtSecurity.validateRequest(token, response);
	}

	@Override
	public boolean validateRequest(String token, long idSection, ResponseWS response) {
		// Creamos el objeto que nos permitirá crear el token
		JWTSecurity jwtSecurity = new JWTSecurity();
		// Regresar respuesta del servicio que recibe el objeto usuario
		return jwtSecurity.validateRequest(token, idSection, response);
	}

	@Override
	public String encrypt(String toEncrypt) throws Exception {
		// Obtener ruta absoluta de la carpeta de empresas
		String relativeWebPath = IResourceManager.ENTERPRISE_PATH;
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

		// Inicializar objeto con ruta de encriptación
		EncrypterCertificatesCodec encrypterCertificatesCodecFile = new EncrypterCertificatesCodec(absoluteDiskPath);
		String encryptS = encrypterCertificatesCodecFile.encrypt(toEncrypt);

		return encryptS;
	}

	@Override
	public String desencrypt(String toDesencrypt) throws Exception {
		// Obtener ruta absoluta de la carpeta de empresas
		String relativeWebPath = IResourceManager.ENTERPRISE_PATH;
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

		// Inicializar objeto con ruta de encriptación
		EncrypterCertificatesCodec encrypterCertificatesCodecFile = new EncrypterCertificatesCodec(absoluteDiskPath);
		String desencryptS = encrypterCertificatesCodecFile.decrypt(toDesencrypt);
//		System.out.println("DesencryptS: " + desencryptS);

		return desencryptS;
	}

	@Override
	public Map<String, Object> refreshToken(String oldToken) throws Exception {
		// Obtener el tiempo de session
		Setting setting = this.settingService.getBasic();
		// Creamos el objeto que nos permitirá crear el token
		JWTSecurity jwtSecurity = new JWTSecurity();
		// Regresar respuesta del servicio que genera el nuevo token
		return jwtSecurity.refreshToken(oldToken, (setting != null ? setting.getSessionTime() : null));
	}

	@Override
	public Long getIdProfile(String token) {
		// Creamos el objeto que nos permitirá crear el token
		JWTSecurity jwtSecurity = new JWTSecurity();
		Object profile = jwtSecurity.getPropertyToken(token, JWTSecurity.CLAIM_PROFILE);
		return profile != null ? Long.parseLong(profile + "") : null;
		// return profileType + "";
	}

	@Override
	public TokenData getTokenData(String token) throws Exception {
		TokenData tokenData = null;
		try {
			tokenData = new TokenData();
			tokenData.setToken(token);
			tokenData.setIdProfile(this.getIdProfile(token));
			tokenData.setIdUser(this.getIdUser(token));
			tokenData.setUsername(this.getUsername(token));
		} catch (Exception e) {
			throw new IntxException(3000, "The token is invalid", e);
		}
		return tokenData;
	}

}
