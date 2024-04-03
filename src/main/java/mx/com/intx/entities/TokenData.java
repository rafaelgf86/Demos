/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;

/**
 * @author INTX
 *
 */
public class TokenData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private long idUser;
	private long idProfile;
	private String username;
	
	public long getIdUser() {
		return idUser;
	}
	public long getIdProfile() {
		return idProfile;
	}
	public String getUsername() {
		return username;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public void setIdProfile(long idProfile) {
		this.idProfile = idProfile;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
