/**
 * mx.com.intx.repository
 */
package mx.com.intx.service;

import mx.com.intx.entities.TokenData;
import mx.com.intx.entities.VersionEntity;

/**
 * @author INTX
 *
 */
public interface IVersionManager {

	/**
	 * Funci�n encargada de obtener los datos de la versi�n actual
	 * 
	 * @param tokenData Objeto con informaci�n de sesi�n
	 * @return
	 */
	VersionEntity get(TokenData tokenData);
}
