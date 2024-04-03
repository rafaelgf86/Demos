/**
 * mx.com.intx.repository
 */
package mx.com.intx.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.intx.entities.Asentamiento;
import mx.com.intx.entities.Estado;
import mx.com.intx.entities.Municipio;
import mx.com.intx.exceptions.IntxException;

/**
 * @author INTX
 *
 */
@Repository(value = "ISepomexDao")
public class SepomexDaoImpl implements ISepomexDao {

	private final static String QUERY_GET_ESTADOS = "SELECT distinct  s.sepomexId.cEstado, s.dEstado FROM Sepomex s ORDER BY s.dEstado ";

	private final static String QUERY_GET_MUNICIPIOS = "SELECT distinct  s.sepomexId.cMnpio, s.dMnpio FROM Sepomex s  "
			+ " WHERE s.sepomexId.cEstado = :cEstado " + " ORDER BY s.dMnpio ";

	private final static String QUERY_GET_ASENTAMIENTOS = "SELECT distinct  s.sepomexId.idAsentaCpcons, s.dAsenta, s.sepomexId.dCodigo FROM Sepomex s  "
			+ " WHERE s.sepomexId.cEstado = :cEstado " + "   AND s.sepomexId.cMnpio = :cMnpio"
			+ "   ORDER BY s.dAsenta ";

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Estado> getEstados() throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			// Inicializar lista de estados
			List<Estado> estados = new ArrayList<Estado>();
			// Crear query
			Query<Object[]> query = session.createQuery(QUERY_GET_ESTADOS, Object[].class);
			// Obtener resultados
			List<Object[]> list = (List<Object[]>) query.list();
			// Guardar resultados en objetos de tipo estado
			for (Object[] arr : list) {
				Estado estado = new Estado();
				estado.setcEstado(arr[0].toString());
				estado.setdEstado(arr[1].toString());
				// Agregar a la lista;
				estados.add(estado);
			}
			return estados;
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (Exception exception) {
			throw new IntxException(1200, "There is a problem with the consult", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<Municipio> getMunicipios(String cEstado) throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			// Inicializar lista de municipios
			List<Municipio> municipios = new ArrayList<Municipio>();
			// Crear query
			Query<Object[]> query = session.createQuery(QUERY_GET_MUNICIPIOS, Object[].class);
			query.setParameter("cEstado", cEstado);
			// Obtener resultados
			List<Object[]> list = (List<Object[]>) query.list();
			// Guardar resultados en objetos de tipo estado
			for (Object[] arr : list) {
				Municipio mun = new Municipio();
				mun.setcMnpio(arr[0].toString());
				mun.setdMnpio(arr[1].toString());
				// Agregar a la lista;
				municipios.add(mun);
			}
			return municipios;
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (Exception exception) {
			throw new IntxException(1200, "There is a problem with the consult", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<Asentamiento> getAsentamientos(String cEstado, String cMnpio) throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			// Inicializar lista de asentamientos
			List<Asentamiento> asentamientos = new ArrayList<Asentamiento>();
			// Crear query
			Query<Object[]> query = session.createQuery(QUERY_GET_ASENTAMIENTOS, Object[].class);
			query.setParameter("cEstado", cEstado);
			query.setParameter("cMnpio", cMnpio);
			// Obtener resultados
    		List<Object[]> list = (List<Object[]>) query.list();
    		// Guardar resultados en objetos de tipo estado
	        for(Object[] arr : list) {
	            Asentamiento asent = new Asentamiento();
	            asent.setIdAsentaCpcons(arr[0].toString());
	            asent.setdAsenta(arr[1].toString());
	            asent.setdCodigo(arr[2].toString());
	            //Agregar a la lista;
	            asentamientos.add(asent);
	        }
			return asentamientos;
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (Exception exception) {
			throw new IntxException(1200, "There is a problem with the consult", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}

}
