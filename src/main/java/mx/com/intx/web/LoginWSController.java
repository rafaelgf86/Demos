package mx.com.intx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;

import mx.com.intx.entities.UserEntity;
import mx.com.intx.service.IJWTSecurityManager;
import mx.com.intx.utils.LoggerIntx;

import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
public class LoginWSController extends DefaultValues {

	/*** Injections ****************************/
	@Autowired
	private IJWTSecurityManager jwtSecurityManager;

	/***
	 * Método encargado de validar y generar el token de usuario La uri es con POST
	 * hacia : http://localhost:8080/CertificateManagerWS/login
	 * 
	 * @RequestParam user : Nombre de usuario
	 * @RequestParam password : Password de usuario
	 */
	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserEntity userEntity) {
		try {
			// Recueperar el token
			Map<String, Object> response = this.jwtSecurityManager.createJWT(userEntity.getUsername(),
					userEntity.getPassword());
			// Regresar el resultado al cliente
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping(value = "/refreshToken", produces = "application/json")
	public ResponseEntity<Map<String, Object>> refreshToken(@RequestHeader("Authorization") String token) {
		try {

			// Recueperar el token
			Map<String, Object> map = this.jwtSecurityManager.refreshToken(token);

			// Regresar el resultado al cliente
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
