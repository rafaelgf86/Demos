/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

/**
 * @author INTX
 *
 */
public class LogEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLog;

	private LogTypeEntity logType;

	private String description;

	private String trace;

	private String serviceName;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime registrationDate;

	private String username;

	private Boolean error;

	public Long getIdLog() {
		return idLog;
	}

	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}

	public LogTypeEntity getLogType() {
		return logType;
	}

	public void setLogType(LogTypeEntity logType) {
		this.logType = logType;
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

	@Override
	public String toString() {
		return "LogEntity [idLog=" + idLog + ", logType=" + logType + ", description=" + description + ", trace="
				+ trace + ", serviceName=" + serviceName + ", registrationDate=" + registrationDate + ", username="
				+ username + ", error=" + error + "]";
	}
}
