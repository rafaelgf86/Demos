/**
 * mx.com.intx.domain
 */
package mx.com.intx.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/** 
 * Entidad persistida de la tabla Log 
 * @author INTX
 */
@Entity
@Table(name="Log_INTX")
public class Log implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Id
	@Column(name="id_log")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLog;
	
	@ManyToOne
	@JoinColumn(name="id_log_type")
	private LogType logType;
	
	@Column(name="description", updatable = false)
	private String description;
	
	@Column(name="trace", updatable = false)
	private String trace;

	@Column(name="service_name", updatable = false)
	private String serviceName;
	
	@Column(name="registration_date", updatable = false)
	private LocalDateTime registrationDate;
	
	@Column(name="username", updatable = false)
	private String username;
	
	@Column(name="error", updatable = false)
	private Boolean error;
	

	/**********************************************************************
	 * Constructors
	 * ********************************************************************/
	public Log() {
		this.registrationDate = LocalDateTime.now();
	}
	
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public Long getIdLog() {
		return idLog;
	}
	
	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
	
	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	@Override
	public String toString() {
		return "Log [idLog=" + idLog + ", logType=" + logType + ", description=" + description + ", trace=" + trace
				+ ", serviceName=" + serviceName + ", registrationDate=" + registrationDate + ", username=" + username
				+ ", error=" + error
				+ "]";
	}

}

