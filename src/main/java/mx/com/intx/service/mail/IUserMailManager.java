/**
 * mx.com.intx.service.mail
 */
package mx.com.intx.service.mail;

import mx.com.intx.domain.User;
import mx.com.intx.entities.TokenData;

/**
 * @author INTX
 *
 */
public interface IUserMailManager {

	void newUserMail( User user, TokenData tokenData ) throws Exception;
}
