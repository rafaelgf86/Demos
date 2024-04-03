/**
 * mx.com.intx.web
 */
package mx.com.intx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import mx.com.intx.entities.VersionEntity;
import mx.com.intx.responses.ObjectResponse;
import mx.com.intx.service.IJWTSecurityManager;
import mx.com.intx.service.IVersionManager;
import mx.com.intx.utils.LoggerIntx;

/**
 * @author INTX
 *
 */
@RestController
public class VersionWSController extends DefaultValues {

	/*******************************************
	 * Injections.
	 *****************************************/
	@Autowired
	private IVersionManager versionManager;

	@Autowired
	private IJWTSecurityManager jwtSecurityManager;

	/*******************************************
	 * Methods.
	 *****************************************/
	@GetMapping(value = "/version", produces = "application/json")
	public ResponseEntity<ObjectResponse> getVersion(@RequestHeader("Authorization") String token) {
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			if (!jwtSecurityManager.validateRequest(token, objectResponse)) {
				return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
			}
			VersionEntity version = this.versionManager.get(jwtSecurityManager.getTokenData(token));
			objectResponse.setObject(version);
			objectResponse.setSuccess(true);
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors(super.getError(e));
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
