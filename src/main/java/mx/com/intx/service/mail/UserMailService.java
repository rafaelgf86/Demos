/**
 * mx.com.intx.service.mail
 */
package mx.com.intx.service.mail;

import java.util.Properties;

import org.springframework.stereotype.Service;

import mx.com.intx.domain.LogType;
import mx.com.intx.domain.User;
import mx.com.intx.entities.TokenData;
import mx.com.intx.utils.EmailUtil;

/**
 * @author INTX
 *
 */
@Service
public class UserMailService extends MailManager implements IUserMailManager {

	@Override
	public void newUserMail(User user, TokenData tokenData) throws Exception {
		try {
			// Si el usuario no ingreso su correo finalizar
			if (user == null || user.getEmail() == null)
				throw new Exception("Email user is not saved");
			// Obtener propiedades del correro configurado para la empresa
			Properties emailConf = super.getProperties(tokenData);
			// de las propiedades obtenemos el usuario remitente
			String remitter = emailConf.getProperty("mail.smtp.user");
			// obtenemos y quitamos la propiedad del pass
			String emailPass = (String) emailConf.remove("password");

			// Generar objeto de mail
			EmailUtil emailUtil = new EmailUtil(emailConf, remitter, emailPass, user.getEmail(), "Alta de usuario",
					"Hola");
			// Ejecutar hilo
			emailUtil.start();

		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.SERVICIO, "New User Main", getClass().getCanonicalName(), exception,
					tokenData);
		}

	}

}
