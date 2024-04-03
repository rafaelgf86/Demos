package mx.com.intx.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import mx.com.intx.domain.Address;
import mx.com.intx.domain.Profile;
import mx.com.intx.domain.Resource;

/**
 * Clase que contiene los datos de usuario
 * @author INTX
 *
 */
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idUser;

	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String name;

	private String lastNameP;

	private String lastNameM;

	private String email;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime registrationDate;

	private Integer statusUser;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	private Profile profile;

	private Address address;

	private Resource userPhoto;

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastNameP() {
		return lastNameP;
	}

	public void setLastNameP(String lastNameP) {
		this.lastNameP = lastNameP;
	}

	public String getLastNameM() {
		return lastNameM;
	}

	public void setLastNameM(String lastNameM) {
		this.lastNameM = lastNameM;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Integer getStatusUser() {
		return statusUser;
	}

	public void setStatusUser(Integer statusUser) {
		this.statusUser = statusUser;
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Resource getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(Resource userPhoto) {
		this.userPhoto = userPhoto;
	}

	@Override
	public String toString() {
		return "UserEntity [idUser=" + idUser + ", username=" + username + ", name=" + name + ", lastNameP=" + lastNameP
				+ ", lastNameM=" + lastNameM + ", email=" + email + ", registrationDate=" + registrationDate
				+ ", statusUser=" + statusUser + ", birthDate=" + birthDate + ", profile=" + profile + ", address=" + address
				+ "]";
	}

	
	

}

