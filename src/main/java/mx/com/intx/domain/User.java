package mx.com.intx.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


/** 
 * Entidad persistida de la tabla User 
 * @author INTX
 */
@Entity
@Table(name="User_INTX")
public class User implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Id
	@Column(name="id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	
	@Column(name="username", updatable = false)
	private String username;

	@Column(name="password")
	private String password;
	
	@ManyToOne
	@JoinColumn(name="id_profile")
	private Profile profile;
	
	@Column(name="name")
	private String name;
	
	@Column(name="last_name_p")
	private String lastNameP;
	
	@Column(name="last_name_m")
	private String lastNameM;
	
	@Column(name="email")
	private String email;
	
	@Column(name="registration_date", updatable = false)
	private LocalDateTime registrationDate;
	
	@Column(name="status_user")
	private Integer statusUser;
	
	@Column(name="logging_attempts")
	private Integer loggingAttempts;
	
	@Column(name="attempting_logging")
	private LocalDateTime attemptingLogging;
	
	@Column(name="birth_date")
	private LocalDate birthDate;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="id_address")
	private Address address;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="user_photo")
	private Resource userPhoto;
	
	
	
	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	public Long getIdUser() {
		return idUser;
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

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
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

	public Integer getLoggingAttempts() {
		return loggingAttempts;
	}

	public void setLoggingAttempts(Integer loggingAttempts) {
		this.loggingAttempts = loggingAttempts;
	}

	public LocalDateTime getAttemptingLogging() {
		return attemptingLogging;
	}

	public void setAttemptingLogging(LocalDateTime attemptingLogging) {
		this.attemptingLogging = attemptingLogging;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
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
	

	@Transient
	public String getCompleteName() {
		return (this.getName() != null ?  this.getName() : "") + " "
		+ (this.getLastNameP() != null ?  this.getLastNameP() : "") + " "
		+ (this.getLastNameM() != null ?  this.getLastNameM() : "");
		
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", username=" + username + ", name=" + name + ", lastNameP=" + lastNameP + ", lastNameM=" + lastNameM + ", email="
				+ email + ", registrationDate=" + registrationDate + ", statusUser=" + statusUser + ", birthDate="
				+ birthDate + ", profile=" + profile 
				+ "]";
	}

	
}
