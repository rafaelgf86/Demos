/**
 * mx.com.intx.service.mail
 */
package mx.com.intx.service.mail;

import java.util.Properties;

import org.springframework.stereotype.Service;

import mx.com.intx.domain.LogType;
import mx.com.intx.entities.TokenData;
import mx.com.intx.utils.EmailUtil;

/**
 * @author INTX
 *
 */
@Service
public class TestMailService extends MailManager implements ITestMailManager {

	@Override
	public boolean testMail(TokenData tokenData) throws Exception {
		try {
			// Obtener propiedades del correro configurado
			Properties emailConf = super.getProperties(tokenData);
			// de las propiedades obtenemos el usuario remitente
			String remitter = emailConf.getProperty("mail.smtp.user");
			// obtenemos y quitamos la propiedad del pass
			String emailPass = (String) emailConf.remove("password");
			// Generar objeto de mail
			EmailUtil emailUtil = new EmailUtil(emailConf, remitter, emailPass, emailConf.getProperty("mail.smtp.user"),
					"Test de Correo", "Hola");
			// Ejecutar hilo
			emailUtil.sendMail();

			return true;
		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.SERVICIO, "Test mail", getClass().getCanonicalName(), exception,
					tokenData);
			return false;
		}
	}

}
