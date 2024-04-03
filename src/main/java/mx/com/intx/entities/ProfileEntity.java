/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;

/**
 * @author INTX
 *
 */
public class ProfileEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idProfile;

	private String name;

	private String description;

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

	@Override
	public String toString() {
		return "ProfileEntity [idProfile=" + idProfile + ", name=" + name + ", description=" + description + "]";
	}
}
