/**
 * mx.com.intx.domain
 */
package mx.com.intx.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** 
 * Entidad persistida de la tabla Log_Type 
 * @author INTX
 */
@Entity
@Table(name="Log_Type_INTX")
public class LogType implements Serializable  {
	
	public static final long CONSULTA               = 1L;
    public static final long NUEVO_REGISTRO         = 2L;
    public static final long ACTUALIZACION_REGISTRO = 3L;
    public static final long ELIMINACION_REGISTRO   = 4L;
    public static final long SERVICIO               = 5L;
    public static final long UPLOAD                 = 6L;
    public static final long SESSION                = 7L;
    public static final long INGRESO_SISTEMA        = 8L;
    public static final long CERTIFICATE            = 9L;

	private static final long serialVersionUID = 1L;

	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Id
	@Column(name="id_log_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLogType; 
	
	@Column(name="log_type_name", updatable = false)
	private String logTypeName;

	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
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
		return "LogType [idLogType=" + idLogType + ", logTypeName=" + logTypeName + "]";
	}
		
}