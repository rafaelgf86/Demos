/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import mx.com.intx.domain.LogType;
import mx.com.intx.entities.TokenData;
import mx.com.intx.entities.VersionEntity;

/**
 * @author INTX
 *
 */
@PropertySource("classpath:version.properties")
@Service
public class VersionService extends Manager implements IVersionManager {

	@Autowired
	private Environment env;

	@Override
	public VersionEntity get(TokenData tokenData) {
		try {
			VersionEntity version = new VersionEntity();
			version.setVersionDate(env.getProperty("version.versionDate"));
			version.setUrlLogo(env.getProperty("version.urlLogo"));
			version.setVersionNumber(env.getProperty("version.versionNumber"));
			version.setUrlLogo(env.getProperty("version.urlLogo"));
			version.setUrlPrivacyPolicies(env.getProperty("version.urlPrivacyPolicies"));
			version.setUrlDocumentation(env.getProperty("version.urlDocumentation"));
			version.setEmailContact(env.getProperty("version.emailContact"));
			return version;
		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.CONSULTA, "Get Version", getClass().getCanonicalName(), exception,
					tokenData);
			throw exception;
		}
	}

}
