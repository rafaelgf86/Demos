/**
 * mx.com.intx.repository
 */
package mx.com.intx.repository;

import java.util.List;

import mx.com.intx.domain.Log;
import mx.com.intx.exceptions.IntxException;

/**
 * @author INTX
 *
 */
public interface ILogDao {

	List<Log> getLogs(int offset, int limit, int orderBy, String order, Long searchIdLogType, Boolean error) throws IntxException;
    long getTotalLogs(Long searchIdLogType, Boolean error) throws IntxException;
}
