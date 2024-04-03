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
 * Entidad persistida de la tabla Resource_Type 
 * @author INTX
 */
@Entity
@Table(name="Resource_Type_INTX")
public class ResourceType implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Id
	@Column(name="id_resource_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idResourceType;
	
	@Column(name="resource_type_name")
	private String resourceTypeName;

	public ResourceType() {
	}
	
	public ResourceType(long idResourceType) {
		this.idResourceType = idResourceType;
	}

	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public Long getIdResourceType() {
		return idResourceType;
	}
	
	public void setIdResourceType(Long idResourceType) {
		this.idResourceType = idResourceType;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	@Override
	public String toString() {
		return "ResourceType [idResourceType=" + idResourceType + ", resourceTypeName=" + resourceTypeName + "]";
	}
	
}