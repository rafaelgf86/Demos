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
 * Entidad persistida de la tabla Resource 
 * @author INTX
 */
@Entity
@Table(name="Resource_INTX")
public class Resource implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	
	@Id
	@Column(name="id_resource")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idResource;

	@Column(name="file_name")
	private String fileName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="file_mime")
	private String fileMime;
	
	@Column(name="file_data")
	private String fileData;
	
	@Column(name="file_path")
	private String filePath;
	
	@ManyToOne
	@JoinColumn(name="id_resource_type")
	private ResourceType resourceType;
	
	@Column(name="username", updatable = false)
	private String username;
	
	@Column(name="registration_date", updatable = false)
	private LocalDateTime registrationDate;
	
	@Column(name="file_size")
	private Long fileSize;
	
	public Resource() {
		this.registrationDate = LocalDateTime.now();
	}
	
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public Long getIdResource() {
		return idResource;
	}

	public void setIdResource(Long idResource) {
		this.idResource = idResource;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public String getFileMime() {
		return fileMime;
	}

	public void setFileMime(String fileMime) {
		this.fileMime = fileMime;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}


	@Override
	public String toString() {
		return "Resource [idResource=" + idResource + ", fileName=" + fileName + ", description=" + description
				/*+ ", fileData=" + fileData*/ 
				+ ", filePath=" + filePath 
				+ ", fileMime=" + fileMime 
				+ ", username=" + username 
				+ ", fileSize=" + fileSize 
				+ ", registrationDate=" + registrationDate 
				+ ", resourceType=" + resourceType
				+ "]";
	}
	
}
