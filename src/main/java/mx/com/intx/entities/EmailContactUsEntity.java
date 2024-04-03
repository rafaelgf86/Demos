/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;

/**
 * @author INTX
 *
 */
public class EmailContactUsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String forname;
	private String surname;
	private String email;
	private String comment;

	public String getForname() {
		return forname;
	}

	public void setForname(String forname) {
		this.forname = forname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "EmailContactUsEntity [forname=" + forname + ", surname=" + surname + ", email=" + email + ", comment="
				+ comment + "]";
	}
}
