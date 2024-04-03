package mx.com.intx.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Embeddable
public class ProfileSectionId  implements Serializable {

	private static final long serialVersionUID = 1L; 
	
	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@OneToOne
	@JoinColumn(name="id_profile",updatable=false,insertable= false)
	private Profile  profile;
	
	@OneToOne
	@JoinColumn(name="id_section",updatable=false,insertable= false)
	private Section  section;
	
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return "ProfileSectionId [profile=" + profile.getName() + ", section=" + section.getName() + "]";
	}

	
}
