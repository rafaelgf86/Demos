/**
 * mx.com.intx.entities
 */
package mx.com.intx.entities;

import java.io.Serializable;

import mx.com.intx.domain.Resource;

/**
 * @author INTX
 *
 */
public class SettingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long sessionTime;
	
	private Resource logo;

	private Integer logoWith;
	
	private Integer logoHeigth;
	
	private Integer backgroundType;
	
	private Resource backgroundImage;

	private String backgroundColor;

	private String mainColor;
	
	private Resource icoImage;
	
	private String mailSmtpAuth;

	private String mailSmtpStarttls;

	private String mailSmtpHost;

	private String mailSmtpPort;

	private String mailSmtpUser;
	
	private String mailSmtpPass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(Long sessionTime) {
		this.sessionTime = sessionTime;
	}

	public Resource getLogo() {
		return logo;
	}

	public void setLogo(Resource logo) {
		this.logo = logo;
	}

	public Integer getLogoWith() {
		return logoWith;
	}

	public void setLogoWith(Integer logoWith) {
		this.logoWith = logoWith;
	}

	public Integer getLogoHeigth() {
		return logoHeigth;
	}

	public void setLogoHeigth(Integer logoHeigth) {
		this.logoHeigth = logoHeigth;
	}

	public Integer getBackgroundType() {
		return backgroundType;
	}

	public void setBackgroundType(Integer backgroundType) {
		this.backgroundType = backgroundType;
	}

	public Resource getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Resource backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getMainColor() {
		return mainColor;
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
	
}
