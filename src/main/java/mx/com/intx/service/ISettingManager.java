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
	 * Función que devuelve los eleméntos básicos de configuración, no requiere autenticación
	 * @return
	 * @throws Exception 
	 */
    public Setting getBasic() throws Exception;
    
    /**
     * Función que regresa todo el eleménto de coonfiguación, requiere autenticación
     * @param tokenData
     * @return
     * @throws Exception 
     */
    public Setting get( TokenData tokenData ) throws Exception;
    
    /**
     * Función que acualiza la configuración
     * @param setting
     * @param tokenData
     * @return
     * @throws Exception
     */
    public Setting update(SettingEntity setting, TokenData tokenData) throws Exception;
}
