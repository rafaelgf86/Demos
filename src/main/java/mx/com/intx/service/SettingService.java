/**
 * mx.com.intx.service
 */
package mx.com.intx.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.intx.domain.LogType;
import mx.com.intx.domain.Setting;
import mx.com.intx.entities.SettingEntity;
import mx.com.intx.entities.TokenData;
import mx.com.intx.repository.IGenericDao;
import mx.com.intx.service.validations.SettingValidations;

/**
 * @author INTX
 *
 */
@Service
public class SettingService extends SettingValidations implements ISettingManager {

    public IGenericDao<Setting> settingDao;
    
    @Autowired
    private IResourceManager resourceManager;
		
    @Autowired
	public void setSettingDao(IGenericDao<Setting> settingDao) {
		this.settingDao = settingDao;
		settingDao.setClazz(Setting.class);
	}
    

	@Override
	public Setting getBasic() throws Exception {
		try {
       		// Obtener los settings a partir del id de la empresa
   	   		Setting settingDB = settingDao.getById(1L);
   	   		if (settingDB == null) {
   	   			return new Setting();
   	   		}
   	   		// Crear nuevo objeto y establecer carácteristicas básicas
   	   		Setting setting = new Setting();
   	   		setting.setBackgroundColor( settingDB.getBackgroundColor() );
   	   		setting.setBackgroundImage( settingDB.getBackgroundImage() );
	   	   	setting.setBackgroundType( settingDB.getBackgroundType() );
	   	   	setting.setIcoImage( settingDB.getIcoImage() );
	   	   	setting.setLogo( settingDB.getLogo() );
	   	   	setting.setLogoHeigth(settingDB.getLogoHeigth());
	   	   	setting.setLogoWith(settingDB.getLogoWith());
	   	   	setting.setMainColor(settingDB.getMainColor());
	   	   	setting.setSessionTime(settingDB.getSessionTime());
   	   		
       		return setting;
       	} catch (Exception exception) {
       		super.logManager.addErrorLog(LogType.CONSULTA, "Get Setting", getClass().getCanonicalName(), exception, null);
           	throw exception;
       	}
	}

	@Override
	public Setting get(TokenData tokenData) throws Exception {
		try {
    		// Obtener los settings a partir del id de la empresa
	   		Setting setting = settingDao.getById(1L);
	   		if (setting == null) {
   	   			return new Setting();
   	   		}
	   		super.setDefaultPass(setting);
    		return setting;
    	} catch (Exception exception) {
    		super.logManager.addErrorLog(LogType.CONSULTA, "Get Setting By Token", getClass().getCanonicalName(), exception, tokenData);
        	throw exception;
    	}	
	}

	@Override
	public Setting update(SettingEntity settingEnt, TokenData tokenData) throws Exception {
		try {    		
    		// Obtener el objeto setting a actualizar
    		Setting settingUpdate = settingDao.getById(1L);
    		Setting setting = convertToDomain(settingEnt);
    		setting.setIdSetting(1L);
    		// Colocar el cliente en el objeto recibido  	 	
	  	 	super.applyAddValidations(settingUpdate, setting);
	  	 	// lista con las imagenes a guardar, en caso de que aplique
	  	 	List<Object> images = new ArrayList<Object>();
	  	 	// Cargar los recursos	
		   	if ( setting.getLogo() != null && (setting.getLogo().getIdResource() == null  || setting.getLogo().getIdResource() < 1)) {
		   		// El método file Manager asignar el path dentro del recurso enviado ( userphpptp )  
		   		this.resourceManager.uploadImage(setting.getLogo(), tokenData);
		   		images.add(setting.getLogo());
		   	}
		   	
			if (setting.getBackgroundImage() != null && (setting.getBackgroundImage().getIdResource() == null
					|| setting.getBackgroundImage().getIdResource() < 1)) {
				this.resourceManager.uploadImage(setting.getBackgroundImage(), tokenData);
				images.add(setting.getBackgroundImage());
			}
		   	
			if (setting.getIcoImage() != null
					&& (setting.getIcoImage().getIdResource() == null || setting.getIcoImage().getIdResource() < 1)) {
				this.resourceManager.uploadImage(setting.getIcoImage(), tokenData);
				images.add(setting.getIcoImage());
			}
//			System.out.println("imges to save: " + images.size());
		   	// si hay un cambio en las imagenes, actualizamos todos los objetos, en caso contrario solo el setting
		   	if (images.isEmpty()) {
		   		
		   		// Actualizar
		   		settingDao.merge(setting);
		   	} else {
		   		// agregamos el objeto de setting al final
		   		images.add(setting);
		   		settingDao.mergeThenDelete(images, null);
		   	}
			// en el password regresamos el default
			super.setDefaultPass(setting);
			// Bitácora
			super.logManager.addLog(LogType.ACTUALIZACION_REGISTRO, "Setting update"  , getClass().getCanonicalName(), setting.toString() , tokenData);
			return setting;
		}catch (Exception exception) {
    		super.logManager.addErrorLog(LogType.ACTUALIZACION_REGISTRO, "Setting update" , getClass().getCanonicalName(), exception, tokenData);
        	throw exception;
    	}
	}

	public Setting convertToDomain(SettingEntity setting) {
		Setting sett = new Setting();
		sett.setBackgroundColor(setting.getBackgroundColor());
		sett.setBackgroundImage(setting.getBackgroundImage());
		sett.setBackgroundType(setting.getBackgroundType());
		sett.setIcoImage(setting.getIcoImage());
		sett.setIdSetting(setting.getId());
		sett.setLogo(setting.getLogo());
		sett.setLogoHeigth(setting.getLogoHeigth());
		sett.setLogoWith(setting.getLogoWith());
		sett.setMailSmtpAuth(setting.getMailSmtpAuth());
		sett.setMailSmtpHost(setting.getMailSmtpHost());
		sett.setMailSmtpPass(setting.getMailSmtpPass());
		sett.setMailSmtpPort(setting.getMailSmtpPort());
		sett.setMailSmtpStarttls(setting.getMailSmtpStarttls());
		sett.setMailSmtpUser(setting.getMailSmtpUser());
		sett.setMainColor(setting.getMainColor());
		sett.setSessionTime(setting.getSessionTime());
		return sett;
	}
	
	public SettingEntity convertToEntiry(Setting setting) {
		SettingEntity sett = new SettingEntity();
		sett.setBackgroundColor(setting.getBackgroundColor());
		sett.setBackgroundImage(setting.getBackgroundImage());
		sett.setBackgroundType(setting.getBackgroundType());
		sett.setIcoImage(setting.getIcoImage());
		sett.setId(setting.getIdSetting());
		sett.setLogo(setting.getLogo());
		sett.setLogoHeigth(setting.getLogoHeigth());
		sett.setLogoWith(setting.getLogoWith());
		sett.setMailSmtpAuth(setting.getMailSmtpAuth());
		sett.setMailSmtpHost(setting.getMailSmtpHost());
		sett.setMailSmtpPass(setting.getMailSmtpPass());
		sett.setMailSmtpPort(setting.getMailSmtpPort());
		sett.setMailSmtpStarttls(setting.getMailSmtpStarttls());
		sett.setMailSmtpUser(setting.getMailSmtpUser());
		sett.setMainColor(setting.getMainColor());
		sett.setSessionTime(setting.getSessionTime());
		return sett;
	}
}
