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
	 * Función encargada de obtener los datos de la versión actual
	 * 
	 * @param tokenData Objeto con información de sesión
	 * @return
	 */
	VersionEntity get(TokenData tokenData);
}
