/**
 * mx.com.intx.service.mail
 */
package mx.com.intx.service.mail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import mx.com.intx.domain.Setting;
import mx.com.intx.entities.TokenData;
import mx.com.intx.exceptions.IntxException;
import mx.com.intx.repository.IGenericDao;
import mx.com.intx.service.Manager;

/**
 * @author INTX
 *
 */
public class MailManager extends Manager {

	public IGenericDao<Setting> settingDao;

	@Autowired
	public void setSettingDao(IGenericDao<Setting> settingDao) {
		this.settingDao = settingDao;
		settingDao.setClazz(Setting.class);
	}

	public Properties getProperties(TokenData tokenData) throws IntxException {
		Setting setting = this.settingDao.getById(1L);
		if (setting == null)
			throw new IntxException(5000, "There is no configuration to send e-mails", null);
		// Generar properties
		Properties properties = new Properties();
		if (setting.getMailSmtpAuth() != null)
			properties.put("mail.smtp.auth", setting.getMailSmtpAuth());
		if (setting.getMailSmtpStarttls() != null)
			properties.put("mail.smtp.starttls.enable", setting.getMailSmtpStarttls());
		if (setting.getMailSmtpHost() != null)
			properties.put("mail.smtp.host", setting.getMailSmtpHost());
		if (setting.getMailSmtpPort() != null)
			properties.put("mail.smtp.port", setting.getMailSmtpPort());
		if (setting.getMailSmtpUser() != null)
			properties.put("mail.smtp.user", setting.getMailSmtpUser());
		if (setting.getMailSmtpPass() != null)
			properties.put("password", setting.getMailSmtpPass());

		if (properties.isEmpty()) {
			throw new IntxException(5000, "There is no configuration to send e-mails", null);
		}
		// Regresar valores
		return properties;
	}
}
