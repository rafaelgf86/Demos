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

@Entity
@Table(name="Section_INTX")
public class Section implements Serializable {

	private static final long serialVersionUID = 1L;
	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Id
	@Column(name="id_section")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSection;

	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description; 
	
	@Column(name="id_parent")
	private Long idParent;
	
	@Column(name="parent")
	private Boolean parent;
	
	@Column(name="enabled")
	private Boolean enabled;
	
	
	
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public Long getIdSection() {
		return idSection;
	}
	public void setIdSection(Long idSection) {
		this.idSection = idSection;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getIdParent() {
		return idParent;
	}
	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}
	public Boolean getParent() {
		return parent;
	}
	public void setParent(Boolean parent) {
		this.parent = parent;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return "Section [idSection=" + idSection + ", name=" + name + ", description=" + description + "]";
	}
}

