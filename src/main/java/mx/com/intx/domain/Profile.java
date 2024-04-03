/**
 * mx.com.intx.domain
 */
package mx.com.intx.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


/** 
 * Entidad persistida de la tabla Profile 
 * @author INTX
 */
@Entity
@Table(name="Profile_INTX")
public class Profile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Id
	@Column(name="id_profile")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idProfile;
	
	@Column(name="name", updatable = false, insertable = false)
	private String name;
	
	@Column(name="description", updatable = false, insertable = false)
	private String description;
	
	// Se generará bajo petición para no generar ciclo infinito
	@Transient
	private List<ProfileSection> profileSections;
		
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
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
	
	public List<ProfileSection> getProfileSections() {
		return profileSections;
	}

	public void setProfileSections(List<ProfileSection> profileSections) {
		this.profileSections = profileSections;
	}
	
	
	/***********************************************************************
	 * toString
	 * *********************************************************************/
	@Override
	public String toString() {
		return "Profile [idProfile=" + idProfile + ", name=" + name + ", description=" + description + "]";
	}	

	
}