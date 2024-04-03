/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;


/**
 * @author INTX
 *
 */
public class LogTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLogType;

	private String logTypeName;

	public Long getIdLogType() {
		return idLogType;
	}

	public void setIdLogType(Long idLogType) {
		this.idLogType = idLogType;
	}

	public String getLogTypeName() {
		return logTypeName;
	}

	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
	}

	@Override
	public String toString() {
		return "LogTypeEntity [idLogType=" + idLogType + ", logTypeName=" + logTypeName + "]";
	}
}
