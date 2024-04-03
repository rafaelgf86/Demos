/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import mx.com.intx.domain.Setting;
import mx.com.intx.entities.SettingEntity;
import mx.com.intx.entities.TokenData;

/**
 * @author INTX
 *
 */
public interface ISettingManager {

	/**
	 * Funci�n que devuelve los elem�ntos b�sicos de configuraci�n, no requiere autenticaci�n
	 * @return
	 * @throws Exception 
	 */
    public Setting getBasic() throws Exception;
    
    /**
     * Funci�n que regresa todo el elem�nto de coonfiguaci�n, requiere autenticaci�n
     * @param tokenData
     * @return
     * @throws Exception 
     */
    public Setting get( TokenData tokenData ) throws Exception;
    
    /**
     * Funci�n que acualiza la configuraci�n
     * @param setting
     * @param tokenData
     * @return
     * @throws Exception
     */
    public Setting update(SettingEntity setting, TokenData tokenData) throws Exception;
}
