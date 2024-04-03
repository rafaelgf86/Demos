/**
 * mx.com.intx.domain
 */
package mx.com.intx.entities;

import java.io.Serializable;

/**
 * @author INTX
 *
 */
public class VersionEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String versionDate;
	private String versionNumber;
	private String urlLogo;
	private String urlPrivacyPolicies;
	private String urlDocumentation;
	private String emailContact;
	
	public String getVersionDate() {
		return versionDate;
	}
	public void setVersionDate(String versionDate) {
		this.versionDate = versionDate;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getUrlLogo() {
		return urlLogo;
	}
	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}
	public String getUrlPrivacyPolicies() {
		return urlPrivacyPolicies;
	}
	public void setUrlPrivacyPolicies(String urlPrivacyPolicies) {
		this.urlPrivacyPolicies = urlPrivacyPolicies;
	}
	public String getUrlDocumentation() {
		return urlDocumentation;
	}
	public void setUrlDocumentation(String urlDocumentation) {
		this.urlDocumentation = urlDocumentation;
	}
	public String getEmailContact() {
		return emailContact;
	}
	public void setEmailContact(String emailContact) {
		this.emailContact = emailContact;
	}
}
