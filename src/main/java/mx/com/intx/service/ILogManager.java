/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.util.List;

import mx.com.intx.domain.Log;
import mx.com.intx.entities.LogTypeEntity;
import mx.com.intx.entities.TokenData;
import mx.com.intx.exceptions.IntxException;
import mx.com.intx.responses.SearchResponse;

/**
 * @author INTX
 *
 */
public interface ILogManager {

	/**
	 * Función para obtener los logs en forma paginada
	 * 
	 * @param offset
	 * @param limit
	 * @param orderBy
	 * @param order
	 * @param searchIdLogType
	 * @param error
	 * @param tokenData
	 * @return
	 * @throws Exception 
	 */
	SearchResponse getLogs(int offset, int limit, int orderBy, String order, Long searchIdLogType, Boolean error,
			TokenData tokenData) throws Exception;

	/**
	 * Función para agregar una bitácora de error, los identificadores se basan en
	 * tokenData
	 * 
	 * @param logType
	 * @param description
	 * @param serviceName
	 * @param exception
	 * @param tokenData
	 * @return
	 */
	Log addErrorLog(long logTypeId, String description, String serviceName, Exception exception, TokenData tokenData);

	/**
	 * Función para una bitácora, los identificadores se basan en tokenData
	 * 
	 * @param logType
	 * @param description
	 * @param serviceName
	 * @param trace
	 * @param tokenData
	 * @return
	 */
	Log addLog(long logTypeId, String description, String serviceName, String trace, TokenData tokenData);

	/**
	 * Función para obtener los tipos de logs del sistema
	 * 
	 * @return
	 * @throws IntxException 
	 */
	List<LogTypeEntity> getLogTypes() throws IntxException;
}
