/**
 * mx.com.intx.web
 */
package mx.com.intx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import mx.com.intx.entities.EmailContactUsEntity;
import mx.com.intx.exceptions.FormException;
import mx.com.intx.responses.ObjectResponse;
import mx.com.intx.service.IJWTSecurityManager;
import mx.com.intx.service.mail.IContactUsManager;
import mx.com.intx.utils.LoggerIntx;

/**
 * @author INTX
 *
 */
@RestController
public class ContactUsWSController extends DefaultValues {

	@Autowired
	private IJWTSecurityManager jwtSecurityManager;

	@Autowired
	private IContactUsManager sevice;
	
	/**
	 * Metodo encargado de enviar un correo del usuario con preguntas y/o dudas, a la direccion configurada en este proyecto
	 * @param token Token de autenticación a través de header
	 * @param form Datos de la persona que envia el correo, junto con el mensaje de esta
	 * @return
	 */
	@PostMapping(value = "/contactUs", headers = "Accept=*/*", produces = "application/json")
	public ResponseEntity<ObjectResponse> contactUs(@RequestHeader("Authorization") String token,
			@RequestBody EmailContactUsEntity form) {
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Validar token
			if (!jwtSecurityManager.validateRequest(token, objectResponse)) {
				return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
			}
			sevice.sendMail(form, jwtSecurityManager.getTokenData(token));
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
}
