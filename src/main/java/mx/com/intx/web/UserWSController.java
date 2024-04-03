/**
 * mx.com.intx.web
 */
package mx.com.intx.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.intx.entities.ProfileEntity;
import mx.com.intx.entities.UserChangePasswordEntity;
import mx.com.intx.entities.UserEntity;
import mx.com.intx.exceptions.FormException;
import mx.com.intx.responses.ObjectResponse;
import mx.com.intx.responses.SearchResponse;
import mx.com.intx.service.IJWTSecurityManager;
import mx.com.intx.service.IProfileManager;
import mx.com.intx.service.IUserManager;
import mx.com.intx.utils.LoggerIntx;

/**
 * @author INTX
 *
 */
@RestController
public class UserWSController extends DefaultValues {
	/*******************************************
     * Sections Id's (validate profile access) .
     * *****************************************/
	private static final long USER_ID_SECTION = 2L;
    
    /*******************************************
     * Injections.
     * *****************************************/ 
    @Autowired
    private IUserManager userManager;

    @Autowired
    private IProfileManager profileManager;
    
	@Autowired
    private IJWTSecurityManager jwtSecurityManager;
	
	
        
    /*******************************************
     * Methods.
     * *****************************************/
    
    /***
     * Método encargado de buscar usuarios en la base de datos.
     * La uri es con GET hacia   : http://localhost:8080/CertificateManagerWS/users
     * @RequestParam offset      : Indica a partir de que posición se obtendran los resultados
     * @RequestParam limit       : Indica el limite de registros a regresar
     * @RequestParam searchName  : En caso de busqueda, el nombre sobre el que se aplicará el like
     * */
    @GetMapping(value="/users", produces = "application/json")
    public ResponseEntity<SearchResponse> getUsers(
    			@RequestHeader("Authorization") String token,
    			@RequestParam(value = "offset",     required = false,defaultValue = OFFSET) int offset,
    			@RequestParam(value = "limit",      required = false,defaultValue = LIMIT) int limit,
    			@RequestParam(value = "orderBy",    required = false,defaultValue = ORDER_BY) int orderBy,
    			@RequestParam(value = "order",      required = false,defaultValue = ORDER) String order,
    			@RequestParam(value = "searchUsername", required = false) String searchUsername
    		){
    	// Inicializar respuesta
    	SearchResponse searchResponse = new SearchResponse();
		try {
			// Validar token vs permisos de perfiles
		   	if( !jwtSecurityManager.validateRequest(token, USER_ID_SECTION, searchResponse) ) {
		   		return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.FORBIDDEN);
		   	}
	        // Recuperar los usuarios y el total de registros con base en los parámetros
	  	 	searchResponse = this.userManager.getUsers( offset, limit, orderBy, order, (searchUsername != null ? searchUsername :"" ), 
	  	 			jwtSecurityManager.getTokenData(token) );
	       
	        // Regresar el resultado al cliente 
	  	 	searchResponse.setSuccess(true);
	        return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
		} catch(Exception e) {
			LoggerIntx.printError(this, e);
			searchResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }	
    }
    
    /***
     * Método encargado de obtener un user en particular
     * La uri es con GET hacia   : http://localhost:8080/CertificateManagerWS/users/1
     * */
    @GetMapping(value="/users/{id}", headers="Accept=*/*",produces = "application/json")
    public ResponseEntity<ObjectResponse> getUser(
    			@RequestHeader("Authorization") String token,
    			@PathVariable Long id
    		){
    	// Inicializar respuesta
    	ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Validar token vs permisos de perfiles
		   	if( !jwtSecurityManager.validateRequest(token, USER_ID_SECTION, objectResponse) ) {
		   		return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
		   	}
		   	
		   	// Recuperar al usuario
	  	 	UserEntity user = this.userManager.get( id, jwtSecurityManager.getTokenData(token) );
	  	 	objectResponse.setObject(user);
	  	 	
	        // Regresar el resultado
	  	 	objectResponse.setSuccess(true);
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }	
    }
    

	/**
	 * Método encargado de agregar un usuario 
	 * La uri es con POST hacia: http://[HOST]:[PORT]/CertificateManagerWS/users
	 * @param token Token de autenticación a través de header
	 * @param user a guardar
	 * @return
	 */	
	@PostMapping(value="/users", produces = "application/json")
	public ResponseEntity<ObjectResponse> addUser(
		@RequestHeader("Authorization") String token,
    	@RequestBody UserEntity user
    ){
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Validar token vs permisos de perfiles
		   	if( !jwtSecurityManager.validateRequest(token, USER_ID_SECTION, objectResponse) ) {
		   		return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
		   	}		   	
		   	
		   	// Salvar al usuario
		   	UserEntity userUpd = this.userManager.add(user, jwtSecurityManager.getTokenData(token));
	  	 	
	  	 	if ( userUpd == null || userUpd.getIdUser() == null)
	  	 		throw new Exception("It was not able to add this User. Check Log");	   	
	  	 	
	  	 	// Regresar el resultado al cliente 
	  	 	objectResponse.setObject(userUpd);
	  	 	objectResponse.setSuccess(true);
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(FormException e) {
			LoggerIntx.printError(this, e);
			// En este caso la respuesta http queda como OK, pero el success queda en falso y se regresan los errores de campo
			objectResponse.setErrors( super.getFormErrors(e) );
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
        } catch(Exception e) {
        	LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	

	/**
	 * Método encargado de actualizar un user 
	 * La uri es con POST hacia: http://[HOST]:[PORT]/CertificateManagerWS/users/{id}
	 * @param token Token de autenticación a través de header
	 * @param user user a guardar
	 * @return
	 */	
	@PutMapping(value="/users/{id}", produces = "application/json")
	public ResponseEntity<ObjectResponse> updateUser(
		@RequestHeader("Authorization") String token,
    	@PathVariable long id,
    	@RequestBody UserEntity user
    ){
		System.out.println("updateUser");
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Validar token vs permisos de perfiles
		   	if( !jwtSecurityManager.validateRequest(token, USER_ID_SECTION, objectResponse) ) {
		   		return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
		   	}  
		   			   	
		   	// Actualizar al cliente
		   	UserEntity userUpd = this.userManager.update(id, user, jwtSecurityManager.getTokenData(token));
		   	
	  	 	// Regresar el resultado al cliente 
	  	 	objectResponse.setObject(userUpd);
	  	 	objectResponse.setSuccess(true);
	  	 	
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(FormException e) {
			LoggerIntx.printError(this, e);
			// En este caso la respuesta http queda como OK, pero el success queda en falso y se regresan los errores de campo
			objectResponse.setErrors( super.getFormErrors(e) );
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
        } catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * Método encargado de actualizar la contraseña de un usuario. 
	 * El id del usuario se toma del token recibido, por lo que el método debe ser utilizado por el usuario en cuestión 
	 * La uri es con POST hacia: http://[HOST]:[PORT]/CertificateManagerWS/changePassword
	 * @param token Token de autenticación a través de header
	 * @return
	 */	
	@PutMapping(value="/changePassword", produces = "application/json")
	public ResponseEntity<ObjectResponse> changePassword(
		@RequestHeader("Authorization") String token,
    	@RequestBody UserChangePasswordEntity userChangePassword
    ){
		System.out.println("updateUser");
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
		   	// Actualizar al cliente
			UserEntity user = this.userManager.updatePassword(userChangePassword, jwtSecurityManager.getTokenData(token));
		   	
	  	 	// Regresar el resultado al cliente 
	  	 	objectResponse.setObject(user);
	  	 	objectResponse.setSuccess(true);
	  	 	
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(FormException e) {
			LoggerIntx.printError(this, e);
			// En este caso la respuesta http queda como OK, pero el success queda en falso y se regresan los errores de campo
			objectResponse.setErrors( super.getFormErrors(e) );
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
        } catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
	 * Método encargado de actualizar un user 
	 * La uri es con POST hacia: http://[HOST]:[PORT]/CertificateManagerWS/users/{id}/status
	 * @param token Token de autenticación a través de header
	 * @param user user a guardar
	 * @return
	 */	
	@PutMapping(value="/users/{id}/status", produces = "application/json")
	public ResponseEntity<ObjectResponse> status(
		@RequestHeader("Authorization") String token,
		@RequestParam(value = "status", required = true) Integer status,
    	@PathVariable Long id
    ){
		System.out.println("updateUser");
		// Inicializar respuesta
		ObjectResponse objectResponse = new ObjectResponse();
		try {
			// Validar token vs permisos de perfiles
		   	if( !jwtSecurityManager.validateRequest(token, USER_ID_SECTION, objectResponse) ) {
		   		return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
		   	}		   	
		   	// Obtener el usuario original de la base de datos (nos aseguramos que sea de la empresa)
		    this.userManager.updateStatus(id, status, jwtSecurityManager.getTokenData(token));		   
	  	 	// Regresar el resultado al cliente 
	  	 	objectResponse.setSuccess(true);	 	
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(FormException e) {
			LoggerIntx.printError(this, e);
			// En este caso la respuesta http queda como OK, pero el success queda en falso y se regresan los errores de campo
			objectResponse.setErrors( super.getFormErrors(e) );
			return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
        } catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/***
     * Método encargado de buscar perfiles en la base de datos.
     * La uri es con GET hacia   : http://localhost:8080/CertificateManagerWS/profiles
     * */
    @GetMapping(value="/profiles", produces = "application/json")
    public ResponseEntity<ObjectResponse> getProfiles(
    			@RequestHeader("Authorization") String token
    		){
    	// Inicializar respuesta
    	ObjectResponse objectResponse = new ObjectResponse();
		try {
		   	if( !jwtSecurityManager.validateRequest(token, objectResponse) ) {
		   		return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.FORBIDDEN);
		   	}
	    	
	        // Recuperar los perfiles
	  	 	List<ProfileEntity> profiles = this.profileManager.getProfiles(jwtSecurityManager.getTokenData(token));
	  	 	objectResponse.setObject(profiles);
	       
	        // Regresar el resultado al cliente 
	  	 	objectResponse.setSuccess(true);
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }	
    }
    
    
    /***
     * Método encargado de obtener el usuario que corresponde al token
     * La uri es con GET hacia   : http://localhost:8080/currentUser
     * */
    @GetMapping(value="/currentUser", headers="Accept=*/*",produces = "application/json")
    public ResponseEntity<ObjectResponse> getCurrentUser(
    			@RequestHeader("Authorization") String token
    		){
    	// Inicializar respuesta
    	ObjectResponse objectResponse = new ObjectResponse();
		try {
		   	// Recuperar al usuario
			UserEntity user = this.userManager.getFromToken( jwtSecurityManager.getTokenData(token) );
	  	 	objectResponse.setObject(user);
	        // Regresar el resultado
	  	 	objectResponse.setSuccess(true);
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }	
    }
	

}
