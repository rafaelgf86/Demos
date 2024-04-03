/**
 * mx.com.intx.service.validations
 */
package mx.com.intx.service.validations;

import mx.com.intx.domain.Setting;
import mx.com.intx.service.Manager;

/**
 * @author INTX
 * TODO faltan validaciones 
 */
public class SettingValidations extends Manager {

	public void applyAddValidations( Setting settingSaved, Setting settingChanged ) throws Exception {
		// TODO faltan validaciones
  	 	getRealPasswords(settingSaved, settingChanged);		
	}
	
	/**
	 * funcion que setea los passwords que ya se tienen registrado en caso de que el objeto asociado 'settingChanged'
	 * contenga el password por defecto 
	 * @param settingSaved Objeto Setting consultado de la bd
	 * @param settingChanged Objeto Setting con los nuevos cambios
	 */
	public void getRealPasswords(Setting settingSaved, Setting settingChanged) {
		// si el password es el default, lo cambiamos al que se tiene registrado
		if (settingChanged.getMailSmtpPass() != null && settingChanged.getMailSmtpPass().equals(DEFAULT_PHRASE)) {
			settingChanged.setMailSmtpPass(settingSaved.getMailSmtpPass());
		}
	}
	
	/**
	 * Función que setea los passwords por default
	 */
	public void setDefaultPass(Setting setting) {
		setting.setMailSmtpPass(setting.getMailSmtpPass() == null || setting.getMailSmtpPass().isEmpty() ? null : DEFAULT_PHRASE);
	}
}
