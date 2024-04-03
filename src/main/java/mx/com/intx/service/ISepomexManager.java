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
	 * Funci�n que obtiene los estados del cat�logo de SEPOMEX
	 * @return
	 * @throws IntxException 
	 */
	List<Estado> getEstadosSepomex() throws IntxException;

	/**
	 * Funci�n que obtiene los municipios que correspondan al estado
	 * @param cEstado Estado correspondiente al identificador indicado
	 * @return
	 * @throws IntxException 
	 */
	List<Municipio> getMunicipiosSepomex(String cEstado) throws IntxException;

	/**
	 * Funci�n que obtiene los asentamientos que correspondan al estado y municipio
	 * @param cEstado Estado correspondiente al identificador indicado
	 * @param cMnpio Municipio correspondiente al identificador indicado
	 * @return
	 * @throws IntxException 
	 */
	List<Asentamiento> getAsentamientosSepomex(String cEstado, String cMnpio) throws IntxException;
	
}
