/**
 * mx.com.intx.domain
 */
package mx.com.intx.domain;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/** 
 * Entidad persistida de la tabla Setting 
 * @author INTX
 */
@Entity
@Table(name="Setting_INTX")
public class Setting implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**********************************************************************
	 * Attributes
	 * ********************************************************************/
	@Id
	@Column(name="id_setting")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSetting;
	
	@Column(name="session_time")
	private Long sessionTime;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="logo")
	private Resource logo;

	@Column(name="logo_with")
	private Integer logoWith;
	
	@Column(name="logo_heigth")
	private Integer logoHeigth;
	
	@Column(name="background_type")
	private Integer backgroundType;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="background_image")
	private Resource backgroundImage;

	@Column(name="background_color")
	private String backgroundColor;

	@Column(name="main_color")
	private String mainColor;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="ico_image")
	private Resource icoImage;
	
	@Column(name="mail_smtp_auth")
	private String mailSmtpAuth;

	@Column(name="mail_smtp_starttls")
	private String mailSmtpStarttls;

	@Column(name="mail_smtp_host")
	private String mailSmtpHost;

	@Column(name="mail_smtp_port")
	private String mailSmtpPort;

	@Column(name="mail_smtp_user")
	private String mailSmtpUser;
	
	@Column(name="mail_smtp_pass")
	private String mailSmtpPass;

	/***********************************************************************
	 * Constructors
	 * *********************************************************************/

	/***********************************************************************
	 * Getters and setters 
	 * *********************************************************************/
	
	public Long getIdSetting() {
		return idSetting;
	}
	
	public void setIdSetting(Long idSetting) {
		this.idSetting = idSetting;
	}

	public Long getSessionTime() {
		return sessionTime;
	}


	public Resource getLogo() {
		return logo;
	}

	public Integer getLogoWith() {
		return logoWith;
	}

	public Integer getLogoHeigth() {
		return logoHeigth;
	}

	public Integer getBackgroundType() {
		return backgroundType;
	}

	public Resource getBackgroundImage() {
		return backgroundImage;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public String getMainColor() {
		return mainColor;
	}


	public void setSessionTime(Long sessionTime) {
		this.sessionTime = sessionTime;
	}

	public void setLogo(Resource logo) {
		this.logo = logo;
	}

	public void setLogoWith(Integer logoWith) {
		this.logoWith = logoWith;
	}

	public void setLogoHeigth(Integer logoHeigth) {
		this.logoHeigth = logoHeigth;
	}

	public void setBackgroundType(Integer backgroundType) {
		this.backgroundType = backgroundType;
	}

	public void setBackgroundImage(Resource backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	public Resource getIcoImage() {
		return icoImage;
	}

	public void setIcoImage(Resource icoImage) {
		this.icoImage = icoImage;
	}
	

	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public void setMailSmtpAuth(String mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	public String getMailSmtpStarttls() {
		return mailSmtpStarttls;
	}

	public void setMailSmtpStarttls(String mailSmtpStarttls) {
		this.mailSmtpStarttls = mailSmtpStarttls;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}

	public String getMailSmtpUser() {
		return mailSmtpUser;
	}

	public void setMailSmtpUser(String mailSmtpUser) {
		this.mailSmtpUser = mailSmtpUser;
	}

	public String getMailSmtpPass() {
		return mailSmtpPass;
	}

	public void setMailSmtpPass(String mailSmtpPass) {
		this.mailSmtpPass = mailSmtpPass;
	}

	@Override
	public String toString() {
		return "Setting [idSetting=" + idSetting + ", sessionTime=" + sessionTime + ", logo=" + logo + ", logoWith="
				+ logoWith + ", logoHeigth=" + logoHeigth + ", backgroundType=" + backgroundType + ", backgroundImage="
				+ backgroundImage + ", backgroundColor=" + backgroundColor + ", mainColor=" + mainColor + ", icoImage="
				+ icoImage + ", mailSmtpAuth=" + mailSmtpAuth + ", mailSmtpStarttls=" + mailSmtpStarttls
				+ ", mailSmtpHost=" + mailSmtpHost + ", mailSmtpPort=" + mailSmtpPort + ", mailSmtpUser=" + mailSmtpUser
				+ ", mailSmtpPass=" + mailSmtpPass + "]";
	}
	
	
	
}

