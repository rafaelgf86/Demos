/**
 * mx.com.intx.service.mail
 */
package mx.com.intx.service.mail;

import java.util.Properties;

import org.springframework.stereotype.Service;

import mx.com.intx.domain.LogType;
import mx.com.intx.entities.EmailContactUsEntity;
import mx.com.intx.entities.TokenData;
import mx.com.intx.exceptions.FormException;
import mx.com.intx.service.validations.ContactUsValidations;
import mx.com.intx.utils.EmailUtil;

/**
 * @author INTX
 *
 */
@Service
public class ContactUsService extends ContactUsValidations implements IContactUsManager {

	@Override
	public void sendMail(EmailContactUsEntity data, TokenData tokenData) throws FormException, Exception {
		// Realizar validaciones básicas
		super.applyAddValidations(data);
		// Obtener propiedades del correro configurado
		Properties emailConf = super.getProperties(tokenData);
		// de las propiedades obtenemos el usuario remitente
		String remitter = emailConf.getProperty("mail.smtp.user");
		// obtenemos y quitamos la propiedad del pass
		String emailPass = (String) emailConf.remove("password");
		String userData = String.format("<p>" + "Datos del usuario:<br/>" + "Nombre: %s<br/>" + "Apellido: %s<br/>"
				+ "e-mail: %s<br/>" + "</p><br/>", data.getForname(), data.getSurname(), data.getEmail());
		// Generar objeto de mail
		EmailUtil emailUtil = new EmailUtil(emailConf, remitter, emailPass, remitter,
				"Correo de 'Contactanos'", userData + data.getComment());
		// Ejecutar hilo
		emailUtil.start();
		super.logManager.addLog(LogType.NUEVO_REGISTRO, "Envio de Correo de 'Contactanos'",
				getClass().getCanonicalName(), data.toString(), tokenData);
	}

}
