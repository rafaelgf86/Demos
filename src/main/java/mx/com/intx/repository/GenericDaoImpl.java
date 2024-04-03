/**
 * mx.com.intx.repository
 */
package mx.com.intx.repository;

import java.util.List;
import java.util.Map;

import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import mx.com.intx.exceptions.IntxException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author INTX
 * @param <T>
 *
 */
@Repository(value = "IGenericDao")
// scope que permite crear una nueva instancia por cada solicitud
@Scope("prototype")
public class GenericDaoImpl<T> implements IGenericDao<T> {
	
	private Class<T> clazz;

	private final static String QUERY_GET_GENERIC = "SELECT x FROM %s x ";
	private final static String QUERY_GET_ALL_PAG = "SELECT x FROM %s x WHERE %s LIKE :%s ";
	private final static String QUERY_GET_TOTAL = "SELECT count(x) FROM %s x WHERE %s LIKE :%s ";
	private final static String QUERY_GET_BY_PROP = "SELECT x FROM %s x WHERE x.%s = :%s ";
	
	private final static String VAL_SEARCH = "searchVal";
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public List<T> getByPropertyEQ(String nameProperty, Object valueProperty) throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			String queryString = String.format(QUERY_GET_BY_PROP, clazz.getSimpleName(), nameProperty, VAL_SEARCH);
			System.out.println("customQ: " + queryString);
			Query<T> query = session.createQuery(queryString, clazz);
			query.setParameter(VAL_SEARCH, valueProperty);
			return query.list();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (Exception exception) {
			throw new IntxException(1200, "There is a problem with the consult by property", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<T> paginatedSearch(int offset, int limit, int orderBy, String order, Map<Integer, String> properties, String searchProperty, String searchValue) throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			String queryString = String.format(QUERY_GET_ALL_PAG, clazz.getSimpleName(), searchProperty, VAL_SEARCH);
			// si no se especifico un orderBy, siempre tomamos la primera propiedad
			if (orderBy < 1) {
				orderBy = 1;
			}
			// en base al orderBy, tomamos la propiedad correspondiente por la cual se
			// ordenara la consulta con base en el mapa orderByProperty, y se concatena el order
			// el cual indica se ASC o DESC
			String queryFinal = String.format("%s ORDER BY %s %s", queryString, properties.get(orderBy), order);
			// Crear objeto query de hibernate
			Query<T> query = session.createQuery(queryFinal, clazz);
			query.setParameter(VAL_SEARCH, "%" + searchValue + "%").setFirstResult(offset).setMaxResults(limit);

			return query.list();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (Exception exception) {
			throw new IntxException(1200, "There is a problem with the paginated consult", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}

	
	@Override
	public long paginatedSearchTotal(String searchProperty, String searchValue) throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			String queryString = String.format(QUERY_GET_TOTAL, clazz.getSimpleName(), searchProperty, VAL_SEARCH);
			Query<Long> query = session.createQuery(queryString, Long.class);
			query.setParameter(VAL_SEARCH, "%" + searchValue + "%");
			return (long) query.getSingleResult();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (Exception exception) {
			throw new IntxException(1200, "There is a problem with the total paginated consult", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	@Override
	public T merge(T model) throws IntxException {
		Session session = null;
		Transaction transaction = null;
		T result = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			result = session.merge(model);
			transaction.commit();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (IllegalStateException  exception) {
			throw new IntxException(1101, "The connection to DB is no longer active", exception);
		} catch (RollbackException exception) {
			if (transaction != null) {
				transaction.rollback();				
			}
			throw new IntxException(1300, "The changes could not be merged successfully. Rolling back changes", exception);
		} finally {
			if (session != null)
				session.close();
		}
		return result;

	}

	@Override
	public void delete(T model) throws IntxException {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.remove(model);
			transaction.commit();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (IllegalStateException  exception) {
			throw new IntxException(1101, "The connection to DB is no longer active", exception);
		} catch (RollbackException exception) {
			if (transaction != null) {
				transaction.rollback();				
			}
			throw new IntxException(1300, "The changes could not be deleted successfully. Rolling back changes", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void mergeThenDelete(List<Object> listToSave, List<Object> listToDelete) throws IntxException {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			if (listToSave != null && !listToSave.isEmpty()) {
				for (Object model : listToSave) {
					session.merge(model);					
				}
			}
			
			if (listToDelete != null && !listToDelete.isEmpty()) {
				for (Object model : listToDelete) {
					session.remove(model);					
				}
			}
			transaction.commit();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (IllegalStateException  exception) {
			throw new IntxException(1101, "The connection to DB is no longer active", exception);
		} catch (RollbackException exception) {
			if (transaction != null) {
				transaction.rollback();				
			}
			throw new IntxException(1300, "The changes could not be merged or deleted successfully. Rolling back changes", exception);
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public void deleteThenMerge(List<Object> listToDelete, List<Object> listToSave) throws IntxException {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			if (listToDelete != null && !listToDelete.isEmpty()) {
				for (Object model : listToDelete) {
					session.remove(model);					
				}
			}

			if (listToSave != null && !listToSave.isEmpty()) {
				for (Object model : listToSave) {
					session.merge(model);					
				}
			}
			
			transaction.commit();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (IllegalStateException  exception) {
			throw new IntxException(1101, "The connection to DB is no longer active", exception);
		} catch (RollbackException exception) {
			if (transaction != null) {
				transaction.rollback();				
			}
			throw new IntxException(1300, "The changes could not be merged or deleted successfully. Rolling back changes", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<T> findAll() throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Query<T> query = session.createQuery(String.format(QUERY_GET_GENERIC, clazz.getSimpleName()), clazz);
			return query.list();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (Exception exception) {
			throw new IntxException(1200, "There is a problem with the consult by property", exception);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void persist(T model) throws IntxException {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.persist(model);
			// ya que la asignacion del id es asincrona, con el flush nos aseguramos de recuperarlo
			session.flush();
			transaction.commit();
		} catch (HibernateException exception) {
			throw new IntxException(1100, "There is a problem with the connection to DB", exception);
		} catch (IllegalStateException  exception) {
			throw new IntxException(1101, "The connection to DB is no longer active", exception);
		} catch (RollbackException exception) {
			if (transaction != null) {
				transaction.rollback();				
			}
			throw new IntxException(1300, "The changes could not be persisted successfully. Rolling back changes", exception);
		}  finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public T getById(Object idValue) throws IntxException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return (T) session.get(clazz, idValue);
		} catch (NoResultException ne ) {
    	    return null;	
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
	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
		
	}


}
