/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.util.List;

import mx.com.intx.domain.ProfileSection;
import mx.com.intx.entities.ProfileEntity;
import mx.com.intx.entities.TokenData;

/**
 * @author INTX
 *
 */
public interface IProfileManager {

	/**
	 * Función que obtiene todos los perfiles que correspondan
	 * @param idProfileType Identificador del tipo de perfile
	 * @param tokenData Objeto con información de sesión
	 * @return Lista de perfiles correspondientes al tipo indicado
	 * @throws Exception
	 */
	List<ProfileEntity> getProfiles(TokenData tokenData) throws Exception;
	
	/**
	 * Función que obtiene el perfil que corresponda al identificador proporcionado
	 * @param idProfile Identificador del perfil
	 * @param tokenData Objeto con información de sesión
	 * @return Perfil correspondiente al identificador indicado
	 * @throws Exception
	 */
	ProfileEntity getProfile(long idProfile, TokenData tokenData) throws Exception;
	
	/**
	 * Función que obtiene las secciones asociadas a un perfil
	 * @param idProfile perfil asociado a las secciones
	 * @return
	 * @throws Exception
	 */
	List<ProfileSection> getProfileSections(long idProfile) throws Exception;
}
