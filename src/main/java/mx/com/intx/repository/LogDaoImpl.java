/**
 * mx.com.intx.repository
 */
package mx.com.intx.repository;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.intx.domain.Log;
import mx.com.intx.exceptions.IntxException;

/**
 * Implementación de la interfaz de LogDao
 * @author INTX
 *
 */
@Repository(value = "ILogDao")
public class LogDaoImpl implements ILogDao {

	@Autowired
	private SessionFactory sessionFactory;

	private final static String QUERY_GET_ALL_PAG = "SELECT l FROM Log l WHERE 1 = 1 ";
	private final static String QUERY_GET_TOTAL = "SELECT count(l) FROM Log l WHERE 1 = 1 ";

	private final static String WHERE_LOG_TYPE = " AND l.logType.idLogType = :searchIdLogType ";
	private final static String WHERE_ERROR = " AND error = :error";

	@Override
	public List<Log> getLogs(int offset, int limit, int orderBy, String order, Long searchIdLogType, Boolean error) throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			String queryString = QUERY_GET_ALL_PAG + (searchIdLogType != null ? WHERE_LOG_TYPE : "")
					+ (error != null ? WHERE_ERROR : "");
			String queryOrderBy = " ORDER BY ";
			if (orderBy == 1) {
				queryOrderBy += " idLog ";
			} else if (orderBy == 2) {
				queryOrderBy += " logType.idLogType ";
			} else if (orderBy == 3) {
				queryOrderBy += " description ";
			} else if (orderBy == 4) {
				queryOrderBy += " serviceName ";
			} else if (orderBy == 5) {
				queryOrderBy += " registrationDate ";
			} else if (orderBy == 6) {
				queryOrderBy += " username ";
			} else if (orderBy == 7) {
				queryOrderBy += " error ";
			}
			// Concatenar tipo de orden asc o desc
			if (order != null)
				queryOrderBy += order;
			// Armar query
			String queryFinal = String.format("%s %s", queryString, queryOrderBy);
			Query<Log> query = session.createQuery(queryFinal, Log.class);
			// Asignar parámetros
			if (searchIdLogType != null)
				query.setParameter("searchIdLogType", searchIdLogType);
			if (error != null)
				query.setParameter("error", error);
			// Asignar paginado
			query.setFirstResult(offset).setMaxResults(limit);
			// Ejecutar
			return query.list();
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
	public long getTotalLogs(Long searchIdLogType, Boolean error) throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
//    		String queryString = QUERY_GET_TOTAL + ( searchIdLogType != null ? WHERE_LOG_TYPE : "" ) + ( error != null ? WHERE_ERROR : "" );
			String queryFinal = String.format("%s %s %s", QUERY_GET_TOTAL,
					(searchIdLogType != null ? WHERE_LOG_TYPE : ""), (error != null ? WHERE_ERROR : ""));
			// Armar query
			Query<Long> query = session.createQuery(queryFinal, Long.class);
			// Asignar parámetros
			if (searchIdLogType != null)
				query.setParameter("searchIdLogType", searchIdLogType);
			if (error != null)
				query.setParameter("error", error);
			// Ejecutar
			return (long) query.getSingleResult();
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
