/**
 * mx.com.intx.domain
 */
package mx.com.intx.domain;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * @author INTX
 *
 */
@Entity 
@Table(name = "Profile_Section_INTX") 
public class ProfileSection implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@EmbeddedId
	private ProfileSectionId profileSectionId;
	
	
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public ProfileSectionId getProfileSectionId() {
		return profileSectionId;
	}
	public void setProfileSectionId(ProfileSectionId profileSectionId) {
		this.profileSectionId = profileSectionId;
	}
	
	@Override
	public String toString() {
		return "ProfileSection [profileSectionId=" + profileSectionId + "]";
	}
	
}