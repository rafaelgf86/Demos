/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.intx.domain.Log;
import mx.com.intx.domain.LogType;
import mx.com.intx.entities.LogEntity;
import mx.com.intx.entities.LogTypeEntity;
import mx.com.intx.entities.TokenData;
import mx.com.intx.exceptions.IntxException;
import mx.com.intx.repository.IGenericDao;
import mx.com.intx.repository.ILogDao;
import mx.com.intx.responses.SearchResponse;
import mx.com.intx.utils.LoggerIntx;

/**
 * @author INTX
 *
 */
@Service
public class LogService extends Manager implements ILogManager {
	
	@Autowired
    private ILogDao logDao;
		
    private IGenericDao<Log> logDaoGeneric;
    private IGenericDao<LogType> logTypeDao;
	
	@Autowired
	public void setLogDaoGeneric(IGenericDao<Log> logDaoGeneric) {
		this.logDaoGeneric = logDaoGeneric;
		logDaoGeneric.setClazz(Log.class);
	}
	@Autowired
	public void setLogTypeDao(IGenericDao<LogType> logTypeDao) {
		this.logTypeDao = logTypeDao;
		logTypeDao.setClazz(LogType.class);
	}

	@Override
	public SearchResponse getLogs(int offset, int limit, int orderBy, String order, Long searchIdLogType, Boolean error,
			TokenData tokenData) throws Exception {
		try { // Obtener el id de la empresa
			List<Log> logs = this.logDao.getLogs(offset, limit, orderBy, order, searchIdLogType, error);
			List<LogEntity> ents = new ArrayList<LogEntity>();
			for (Log l : logs) {
				ents.add(convertToEntity(l));
			}
			Long totalLogs = this.logDao.getTotalLogs(searchIdLogType, error);
			return super.getSearchResponse(offset, limit, ents, totalLogs);
		} catch (Exception exception) {
			this.addErrorLog(LogType.CONSULTA, "Get Logs", getClass().getCanonicalName(), exception, tokenData);
    		throw exception;
		}
	}

	@Override
	public Log addErrorLog(long logTypeId, String description, String serviceName, Exception exception,
			TokenData tokenData) {
		try { 
			// Obtener el tipo de log
			LogType logType = this.logTypeDao.getById(logTypeId);
			// Validat tipo de log
			if ( logType == null )
				throw new Exception("LogType not found ("+logTypeId+")");
			
			Log log = new Log();
			// Convertir la excepción a una cadena
			String trace = null;
			if (exception!=null){
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				exception.printStackTrace(pw);
				trace = sw.toString();
				// validar si la excepcion es propia, obtener el codigo de error
//				if (exception instanceof IntxException) {
//					log.setErrorCode(((IntxException)exception).getCode());
//				}
			}
			
			log.setLogType(logType);
			log.setTrace(trace);
			log.setDescription(description);
			log.setError(true);
			if ( tokenData != null ) {
				log.setUsername( tokenData.getUsername() );
			} else {
				log.setUsername( "System" );
			}
			log.setServiceName(serviceName);
			
			this.logDaoGeneric.persist(log);
			return log;
		} catch (Exception e) { 
			LoggerIntx.printError("LogService", e);
			return null;
		}
	}

	@Override
	public Log addLog(long logTypeId, String description, String serviceName, String trace, TokenData tokenData) {
		try { 
			// Obtener el tipo de log
			LogType logType = this.logTypeDao.getById(logTypeId);
			// Validat tipo de log
			if ( logType == null )
				throw new Exception("LogType not found ("+logTypeId+")");
						
			Log log = new Log();
			log.setLogType(logType);
			log.setDescription(description);
			log.setTrace(trace);
			log.setError(false);
			if ( tokenData != null ) {
				log.setUsername( tokenData.getUsername() );
			} else {
				log.setUsername( "System" );
			}
			log.setServiceName(serviceName);			
			this.logDaoGeneric.persist(log);
			return log;
		} catch (Exception e) { 
			LoggerIntx.printError("LogService", e);
			return null;
		}
	}

	@Override
	public List<LogTypeEntity> getLogTypes() throws IntxException {
		List<LogType> types = logTypeDao.findAll();
		List<LogTypeEntity> result = new ArrayList<LogTypeEntity>();
		for (LogType t : types) {
			result.add(convertToEntity(t));
		}
		return result;
	}
	
	public LogTypeEntity convertToEntity(LogType logType) {
		LogTypeEntity l = new LogTypeEntity();
		l.setIdLogType(logType.getIdLogType());
		l.setLogTypeName(logType.getLogTypeName());
		return l;
	}
	
	public LogEntity convertToEntity(Log log) {
		LogEntity l = new LogEntity();
		l.setDescription(log.getDescription());
		l.setError(log.getError());
		l.setIdLog(log.getIdLog());
		l.setLogType(convertToEntity(log.getLogType()));
		l.setRegistrationDate(log.getRegistrationDate());
		l.setServiceName(log.getServiceName());
		l.setTrace(log.getTrace());
		l.setUsername(log.getUsername());
		return l;
	}

}
