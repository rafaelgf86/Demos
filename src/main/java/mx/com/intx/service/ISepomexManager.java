/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.util.List;

import mx.com.intx.entities.Asentamiento;
import mx.com.intx.entities.Estado;
import mx.com.intx.entities.Municipio;
import mx.com.intx.exceptions.IntxException;

/**
 * @author INTX
 *
 */
public interface ISepomexManager {

	/**
	 * Función que obtiene los estados del catálogo de SEPOMEX
	 * @return
	 * @throws IntxException 
	 */
	List<Estado> getEstadosSepomex() throws IntxException;

	/**
	 * Función que obtiene los municipios que correspondan al estado
	 * @param cEstado Estado correspondiente al identificador indicado
	 * @return
	 * @throws IntxException 
	 */
	List<Municipio> getMunicipiosSepomex(String cEstado) throws IntxException;

	/**
	 * Función que obtiene los asentamientos que correspondan al estado y municipio
	 * @param cEstado Estado correspondiente al identificador indicado
	 * @param cMnpio Municipio correspondiente al identificador indicado
	 * @return
	 * @throws IntxException 
	 */
	List<Asentamiento> getAsentamientosSepomex(String cEstado, String cMnpio) throws IntxException;
	
}
