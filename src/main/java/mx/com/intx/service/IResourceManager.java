/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import mx.com.intx.domain.Resource;
import mx.com.intx.entities.TokenData;

/**
 * @author INTX
 *
 */
public interface IResourceManager {

	public static final String ENTERPRISE_PATH = "/enterprise/INTX_1591814152453/";

	/**
	 * Función que carga un Resource de tipo IMAGE al servidor, ademas de setear
	 * algunas propiedades del objeto Resource en base a los parametros recibidos.
	 * 
	 * @param resource  Resource a actualizar y a cargar en el servidor
	 * @param tokenData Objeto con información de sesión
	 * @throws Exception Excepción al cargar el archivo
	 */
	void uploadImage(Resource resource, TokenData tokenData) throws Exception;

	/**
	 * Función que carga un Resource de tipo DOCUMENT al servidor, ademas de setear
	 * algunas propiedades del objeto Resource en base a los parametros recibidos.
	 * 
	 * @param resource  Resource a actualizar y a cargar en el servidor
	 * @param tokenData Objeto con información de sesión
	 * @throws Exception Excepción al cargar el archivo
	 */
	void uploadDocument(Resource resource, TokenData tokenData) throws Exception;

	/**
	 * Función que carga un Resource de tipo VIDEO al servidor, ademas de setear
	 * algunas propiedades del objeto Resource en base a los parametros recibidos.
	 * 
	 * @param resource  Resource a actualizar
	 * @param tokenData Objeto con información de sesión
	 * @throws Exception Excepción al cargar el archivo
	 */
	void uploadVideo(Resource resource, TokenData tokenData) throws Exception;

	/**
	 * Funcion que elimina el archivo fisico asociado a el resource recibido, en la
	 * ruta que tiene especificada en sus propiedades
	 * 
	 * @param resource
	 * @throws Exception
	 */
	void deleteLocalResource(Resource resource) throws Exception;
}
