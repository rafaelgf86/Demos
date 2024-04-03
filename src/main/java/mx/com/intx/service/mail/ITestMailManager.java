/**
 * mx.com.intx.service.mail
 */
package mx.com.intx.service.mail;

import mx.com.intx.entities.TokenData;

/**
 * @author INTX
 *
 */
public interface ITestMailManager {

	boolean testMail( TokenData tokenData ) throws Exception;
}
