/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.util.List;

import mx.com.intx.domain.User;
import mx.com.intx.entities.TokenData;
import mx.com.intx.entities.UserChangePasswordEntity;
import mx.com.intx.entities.UserEntity;
import mx.com.intx.responses.SearchResponse;

/**
 * @author INTX
 *
 */
public interface IUserManager {

	/**
	 * Función para obtener los reportes en forma paginada
	 * @param offset Posición inicial de la búsqueda
	 * @param limit Cantidad de datos a recuperar
	 * @param orderBy Campo de ordenamiento
	 * @param order Modo de ordenamiento: ascendente o descendente
	 * @param searchUsername Coincidencia con el nombre del usuario que se esta buscando
	 * @param tokenData Objeto con información de sesión
	 * @return Objeto de respuesta paginada 
	 * @throws Exception 
	 */
	SearchResponse getUsers(int offset, int limit, int orderBy, String order,String searchUsername, TokenData tokenData) throws Exception;
    
    UserEntity get(long id, TokenData tokenData) throws Exception;
    
    List<UserEntity> getAllUsers(TokenData tokenData) throws Exception;

	User getByUsername(String username) throws Exception;
	
	UserEntity update(long idUser, UserEntity userEnt, TokenData tokenData) throws Exception;
    
    UserEntity updatePassword(UserChangePasswordEntity userChangePassword, TokenData tokenData) throws Exception;
    
    UserEntity add(UserEntity userEnt, TokenData tokenData) throws Exception;
    
    boolean updateStatus(long idUser, Integer status, TokenData tokenData) throws Exception; 
    
    // Para obtenerse asi mismo
    UserEntity getFromToken(TokenData tokenData) throws Exception;

}
