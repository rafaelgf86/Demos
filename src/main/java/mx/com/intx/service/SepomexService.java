/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.intx.entities.Asentamiento;
import mx.com.intx.entities.Estado;
import mx.com.intx.entities.Municipio;
import mx.com.intx.exceptions.IntxException;
import mx.com.intx.repository.ISepomexDao;

/**
 * @author INTX
 *
 */
@Service
public class SepomexService extends Manager implements ISepomexManager {

	@Autowired
    private ISepomexDao sepomexDao;
	
	@Override
	public List<Estado> getEstadosSepomex() throws IntxException {
    	return sepomexDao.getEstados(); 
	}
	@Override
	public List<Municipio> getMunicipiosSepomex(String cEstado) throws IntxException {
		return sepomexDao.getMunicipios(cEstado); 
	}
	@Override
	public List<Asentamiento> getAsentamientosSepomex(String cEstado, String cMnpio) throws IntxException {
		return sepomexDao.getAsentamientos(cEstado, cMnpio); 
	}

}
