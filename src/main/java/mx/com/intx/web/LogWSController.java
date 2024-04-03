/**
 * mx.com.intx.web
 */
package mx.com.intx.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.intx.entities.LogTypeEntity;
import mx.com.intx.responses.ObjectResponse;
import mx.com.intx.responses.SearchResponse;
import mx.com.intx.service.IJWTSecurityManager;
import mx.com.intx.service.ILogManager;
import mx.com.intx.utils.LoggerIntx;

/**
 * @author INTX
 *
 */
@RestController
public class LogWSController extends DefaultValues {

	/*******************************************
	 * Sections Id's (validate profile access) .
	 *****************************************/
	private static final long LOG_ID_SECTION = 1L;

	/*******************************************
	 * Injections.
	 *****************************************/
	@Autowired
	private ILogManager logManager;

	@Autowired
	private IJWTSecurityManager jwtSecurityManager;

	/*******************************************
	 * Methods.
	 *****************************************/

	/***
	 * Método encargado de buscar logs en la base de datos. La uri es con GET hacia
	 * : http://localhost:8080/InnovatixControl/logs
	 * 
	 * @RequestParam offset : Indica a partir de que posición se obtendran los
	 *               resultados
	 * @RequestParam limit : Indica el limite de registros a regresar
	 * @RequestParam searchLogType : En caso de busqueda, el nombre sobre el que se
	 *               aplicará la búsqueda
	 */
	@GetMapping(value = "/logs", headers = "Accept=*/*", produces = "application/json")
	public ResponseEntity<SearchResponse> search(@RequestHeader("Authorization") String token,
			@RequestParam(value = "offset", required = false, defaultValue = OFFSET) Integer offset,
			@RequestParam(value = "limit", required = false, defaultValue = LIMIT) Integer limit,
			@RequestParam(value = "orderBy", required = false, defaultValue = ORDER_BY) Integer orderBy,
			@RequestParam(value = "order", required = false, defaultValue = ORDER) String order,
			@RequestParam(value = "searchLogType", required = false) Long searchLogType,
			@RequestParam(value = "searchError", required = false) Boolean searchError) {
		// Inicializar respuesta
		SearchResponse searchResponse = new SearchResponse();
		try {
			// Validar token vs permisos de perfiles
			if (!jwtSecurityManager.validateRequest(token, LOG_ID_SECTION, searchResponse)) {
				return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.FORBIDDEN);
			}
			// Recuperar las empresas y el total de registros con base en los parámetros
			searchResponse = this.logManager.getLogs(offset, limit, orderBy, order, searchLogType, searchError,
					jwtSecurityManager.getTokenData(token));

			// Regresar el resultado al cliente
			searchResponse.setSuccess(true);
			return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			searchResponse.setErrors(super.getError(e));
			return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/***
	 * Método encargado de buscar los tipos de logs que existen en el sistema
	 */
	@GetMapping(value = "/logTypes", headers = "Accept=*/*", produces = "application/json")
	public ResponseEntity<ObjectResponse> getLogTypes() {
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Recuperar tipos de logs
			List<LogTypeEntity> logsTypes = this.logManager.getLogTypes();
			// Agregar a resuesta
			objectResponse.setObject(logsTypes);
			// Regresar el resultado al cliente
			objectResponse.setSuccess(true);
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch (Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors(super.getError(e));
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
