/**
 * mx.com.intx.repository
 */
package mx.com.intx.repository;

import java.util.List;

import mx.com.intx.entities.Asentamiento;
import mx.com.intx.entities.Estado;
import mx.com.intx.entities.Municipio;
import mx.com.intx.exceptions.IntxException;

/**
 * @author INTX
 *
 */
public interface ISepomexDao {

	// Objetos particulares, directo para combos
	List<Estado>       getEstados() throws IntxException;
	List<Municipio>    getMunicipios(String cEstado) throws IntxException;
	List<Asentamiento> getAsentamientos(String cEstado, String cMnpio) throws IntxException;
	
}
