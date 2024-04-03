/**
 * mx.com.intx.service.mail
 */
package mx.com.intx.service.mail;

import mx.com.intx.entities.EmailContactUsEntity;
import mx.com.intx.entities.TokenData;
import mx.com.intx.exceptions.FormException;

/**
 * @author INTX
 *
 */
public interface IContactUsManager {

	/**
	 * Metodo encargado de enviar un correo del usuario con preguntas y/o dudas, a la direccion configurada en este proyecto
	 * @param data Datos de la persona que envia el correo, junto con el mensaje de esta
	 * @param tokenData Datos en claro del token
	 * @throws FormException 
	 * @throws Exception 
	 */
	void sendMail(EmailContactUsEntity data, TokenData tokenData) throws FormException, Exception;
}
