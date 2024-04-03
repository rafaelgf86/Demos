/**
 * mx.com.intx.web
 */
package mx.com.intx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import mx.com.intx.domain.Setting;
import mx.com.intx.entities.SettingEntity;
import mx.com.intx.exceptions.FormException;
import mx.com.intx.responses.ObjectResponse;
import mx.com.intx.service.IJWTSecurityManager;
import mx.com.intx.service.ISettingManager;
import mx.com.intx.service.mail.ITestMailManager;
import mx.com.intx.utils.LoggerIntx;

/**
 * @author INTX
 *
 */
@RestController
public class SettingWSController extends DefaultValues {

	/*******************************************
	 * Sections Id's (validate profile access) .
	 *****************************************/
	private static final Long SETTING_ID_SECTION = 3L;

	/*******************************************
	 * Injections.
	 *****************************************/
	@Autowired
	private ISettingManager settingManager;

	@Autowired
	private ITestMailManager testMailManager;

	@Autowired
	private IJWTSecurityManager jwtSecurityManager;

	/*******************************************
	 * Methods.
	 *****************************************/

	/***
	 * Método encargado de obtener los seeting de una empresa, no se requiere
	 * autenticación La uri es con GET hacia :
	 * http://localhost:8080/InnovatixControl/settings
	 */
	@GetMapping(value = "/settings/basic", headers = "Accept=*/*", produces = "application/json")
	public ResponseEntity<ObjectResponse> getSettingsBasic() {
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Recuperar los seetings de BD
			Setting setting = this.settingManager.getBasic();
			objectResponse.setObject(setting);
			// Regresar el resultado
			objectResponse.setSuccess(true);
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors(super.getError(e));
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/***
	 * Método encargado de obtener los seeting de una empresa, se requiere
	 * autenticación La uri es con GET hacia :
	 * http://localhost:8080/InnovatixControl/settings
	 */
	@GetMapping(value = "/settings", headers = "Accept=*/*", produces = "application/json")
	public ResponseEntity<ObjectResponse> getSettings(@RequestHeader("Authorization") String token) {
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Validar token vs permisos de perfiles
			if (!jwtSecurityManager.validateRequest(token, SETTING_ID_SECTION, objectResponse)) {
				return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
			}
			// Recuperar los seetings de BD
			Setting setting = this.settingManager.get(jwtSecurityManager.getTokenData(token));
			objectResponse.setObject(setting);
			// Regresar el resultado
			objectResponse.setSuccess(true);
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors(super.getError(e));
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Método encargado de actualizar un setting La uri es con POST hacia:
	 * http://[HOST]:[PORT]/InnovatixControl/settings/{id}
	 * 
	 * @param token Token de autenticación a través de header
	 * @param job   job a guardar
	 * @return
	 */
	@PutMapping(value = "/settings", produces = "application/json")
	public ResponseEntity<ObjectResponse> updateJob(@RequestHeader("Authorization") String token,
			@RequestBody SettingEntity settingEnt) {
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Validar token vs permisos de perfiles
			if (!jwtSecurityManager.validateRequest(token, SETTING_ID_SECTION, objectResponse)) {
				return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
			}

			// Actualizar el job
			Setting setting = this.settingManager.update(settingEnt, jwtSecurityManager.getTokenData(token));

			// Regresar el resultado al cliente
			objectResponse.setObject(setting);
			objectResponse.setSuccess(true);
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch (FormException e) {
			LoggerIntx.printError(this, e);
			// En este caso la respuesta http queda como OK, pero el success queda en falso
			// y se regresan los errores de campo
			objectResponse.setErrors(super.getFormErrors(e));
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors(super.getError(e));
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/***
	 * Método encargado de obtener los seeting de una empresa, se requiere
	 * autenticación La uri es con GET hacia :
	 * http://localhost:8080/InnovatixControl/settings
	 */
	@GetMapping(value = "/settings/testMail", headers = "Accept=*/*", produces = "application/json")
	public ResponseEntity<ObjectResponse> testMail(@RequestHeader("Authorization") String token) {
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		// Validar token vs permisos de perfiles
		if (!jwtSecurityManager.validateRequest(token, SETTING_ID_SECTION, objectResponse)) {
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
		}
		try {
			// Recuperar los seetings de BD
			this.testMailManager.testMail(jwtSecurityManager.getTokenData(token));
			objectResponse.setObject(true);
			// Regresar el resultado
			objectResponse.setSuccess(true);
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors(super.getError(e));
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
