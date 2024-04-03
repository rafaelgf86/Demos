/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.intx.domain.LogType;
import mx.com.intx.domain.Profile;
import mx.com.intx.domain.User;
import mx.com.intx.entities.TokenData;
import mx.com.intx.entities.UserChangePasswordEntity;
import mx.com.intx.entities.UserEntity;
import mx.com.intx.exceptions.FormException;
import mx.com.intx.faults.FormFault;
import mx.com.intx.repository.IGenericDao;
import mx.com.intx.responses.SearchResponse;
import mx.com.intx.service.mail.IUserMailManager;
import mx.com.intx.service.validations.UserValidations;

/**
 * @author INTX 
 */
@Service
public class UserService extends UserValidations implements IUserManager {

	@Autowired
	private IResourceManager resourceManager;
	
	@Autowired
	private IUserMailManager userMailManager;
	
	protected IGenericDao<Profile> daoProfile;

	@Autowired
	public void setDaoProfile(IGenericDao<Profile> daoProfile) {
		this.daoProfile = daoProfile;
		daoProfile.setClazz(Profile.class);
	}

	@Override
	public SearchResponse getUsers(int offset, int limit, int orderBy, String order, String searchUsername,
			TokenData tokenData) throws Exception {
		try {
			Map<Integer, String> orderByProperty = new HashMap<Integer, String>();
			orderByProperty.put(1, "idUser");
			orderByProperty.put(2, "username");
			orderByProperty.put(3, "profile.name");
			orderByProperty.put(4, "name");
			orderByProperty.put(5, "email");
			orderByProperty.put(6, "registrationDate");
			orderByProperty.put(7, "statusUser");
			orderByProperty.put(8, "birthDate");

			List<User> users = daoUser.paginatedSearch(offset, limit, orderBy, order, orderByProperty, "username",
					searchUsername);
			List<UserEntity> userEntities = this.convertToEntity(users);
			long totalUsers = daoUser.paginatedSearchTotal("username", searchUsername);
			return super.getSearchResponse(offset, limit, userEntities, totalUsers);
		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.CONSULTA, "Get Users", getClass().getCanonicalName(), exception,
					tokenData);
			throw exception;
		}
	}

	@Override
	public UserEntity get(long id, TokenData tokenData) throws Exception {
		try {
			// Generar respuestas
			User user = this.daoUser.getById(id);
			return convertToEntity(user);
		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.CONSULTA, "Get User", getClass().getCanonicalName(), exception,
					tokenData);
			throw exception;
		}
	}

	@Override
	public List<UserEntity> getAllUsers(TokenData tokenData) throws Exception {
		try {
			// Realizar consultas
			List<User> users = daoUser.findAll();
			// Generar respuestas
			return this.convertToEntity(users);
		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.CONSULTA, "Get All Users", getClass().getCanonicalName(), exception,
					tokenData);
			throw exception;
		}
	}

	@Override
	public User getByUsername(String username) throws Exception {
		try {
			// Generar respuestas
			List<User> users = this.daoUser.getByPropertyEQ("username", username);
			return users.isEmpty() ? null : users.get(0);
		} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.CONSULTA, "Get User By Username", getClass().getCanonicalName(),
					exception, null);
			throw exception;
		}
	}

	@Override
	public UserEntity update(long idUser, UserEntity userEnt, TokenData tokenData) throws Exception {
		try {

		   	// Obtener el usuario original de la base de datos
		   	User userOriginal = this.daoUser.getById(idUser);
		   	User user = convertToDomain(userEnt);
		   	// En el método de actualización no será posible actualizar el password
		   	// por lo que se asigna el original
		   	user.setPassword(userOriginal.getPassword());
		   	// Realizar validaciones 
		 	super.applyUpdValidations( user, userOriginal );	 

			// Revisar si se cargo su foto para almacenar y obtener el path
			if (user.getUserPhoto() != null /* && user.getUserPhoto().getFileData() != null */ ) {
				// El método file Manager asignar el path dentro del recurso enviado (
				// userphpptp )
				this.resourceManager.uploadImage(user.getUserPhoto(), tokenData);
			}
			// Actualizar usuario
			daoUser.merge(user);
			super.logManager.addLog(LogType.ACTUALIZACION_REGISTRO, "User: " + user.getUsername(),
					getClass().getCanonicalName(), user.toString(), tokenData);
			return convertToEntity(user);
		} catch (FormException formException) {
        	throw formException;
    	} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.ACTUALIZACION_REGISTRO, "Update User", getClass().getCanonicalName(),
					exception, tokenData);
			throw exception;
		}
	}

	@Override
	public UserEntity updatePassword(UserChangePasswordEntity userChangePassword, TokenData tokenData)
			throws Exception {
		try {
			// Obtener el id del usuario
			User user = this.daoUser.getById(tokenData.getIdUser());
			// Revisar si existe
			if (user == null)
				throw new FormException("Form Exception", Arrays.asList(new FormFault("V_USR_000", "idUser", "The user does not exist")));

			// Obtener anterior password
			String passwordDes = super.desencrypt(user.getPassword());
			// Revisar que el anterior password sea el mismo que el old
			if (!passwordDes.equals(userChangePassword.getOldPassword()))
				throw new FormException("Form Exception", Arrays.asList(new FormFault("V_USR_023", "password", "The old password is invalid")));

			// Asignar nuevo password a usuario
			user.setPassword(super.encrypt(userChangePassword.getNewPassword()));
			// Actualizar usuario
			daoUser.merge(user);
			super.logManager.addLog(LogType.ACTUALIZACION_REGISTRO, "Change Password: " + user.getUsername(),
					getClass().getCanonicalName(), user.toString(), tokenData);
			return convertToEntity(user);
		} catch (FormException formException) {
        	throw formException;
    	} catch (Exception exception) {
			super.logManager.addErrorLog(LogType.ACTUALIZACION_REGISTRO, "Update password", getClass().getCanonicalName(),
					exception, tokenData);
			throw exception;
		}
	}

	@Override
	public UserEntity add(UserEntity userEnt, TokenData tokenData) throws Exception {
		try { 
			User user = convertToDomain(userEnt);
			// Realizar validaciones básicas
			super.applyAddValidations( user );
			// Obtener el perfil solicitado
			Profile profile = this.daoProfile.getById( user.getProfile().getIdProfile() );
			user.setProfile(profile);			
    		// Asignar fecha y estatus inicial
	  	 	user.setRegistrationDate(LocalDateTime.now());
	  	 	user.setStatusUser(1);	
	  	 	// Encriptar la contraseña
	  	 	String password = super.encrypt(user.getPassword());
	  	 	user.setPassword( password );	
	  	 	// Revisar si se cargo su foto para almacenar y obtener el path
		   	if ( user.getUserPhoto() != null /*&& user.getUserPhoto().getFileData() != null*/ ) {
		   		// El método file Manager asignar el path dentro del recurso enviado ( userphpptp )  
		   		this.resourceManager.uploadImage(user.getUserPhoto(), tokenData);
		   	}
	  	 	// Guardar usuario
		   	daoUser.merge(user);
			super.logManager.addLog(LogType.NUEVO_REGISTRO, "User: " + user.getUsername() , getClass().getCanonicalName(), user.toString() , tokenData);
			
			// Finalmente intentar enviar un correo (En caso de error solo se escribe en bitacora)
			this.userMailManager.newUserMail(user, tokenData);
			
			return convertToEntity(user);
		} catch (FormException formException) {
        	throw formException;
    	} catch (Exception exception) {
    		super.logManager.addErrorLog(LogType.NUEVO_REGISTRO, "Add " + userEnt.getUsername(), getClass().getCanonicalName(), exception, tokenData);
        	throw exception;
    	}
	}

	@Override
	public boolean updateStatus(long idUser, Integer status, TokenData tokenData) throws Exception {
		try { 
			// Obtener usuario anterior
			User usuarioUpdate = this.daoUser.getById(idUser);
			// Revisar si existe
			if (usuarioUpdate == null)
				throw new FormException("Form Exception", Arrays.asList(new FormFault("V_USR_000", "idUser", "The user does not exist")));

			// Actualizar status
			usuarioUpdate.setStatusUser( status );
			// Actualizar usuario
			daoUser.merge(usuarioUpdate);
			super.logManager.addLog(LogType.ACTUALIZACION_REGISTRO, "Status User: " + usuarioUpdate.getUsername() , getClass().getCanonicalName(), usuarioUpdate.toString() , tokenData);
			return true;
		} catch (FormException formException) {
        	throw formException;
    	} catch (Exception exception) {
    		super.logManager.addErrorLog(LogType.ACTUALIZACION_REGISTRO, "Update Status", getClass().getCanonicalName(), exception, tokenData);
        	throw exception;
    	}
	}

	@Override
	public UserEntity getFromToken(TokenData tokenData) throws Exception {
		try { 
       		return convertToEntity(this.daoUser.getById(tokenData.getIdUser()));
       	}catch (Exception exception) {
       		super.logManager.addErrorLog(LogType.CONSULTA, "Get User", getClass().getCanonicalName(), exception, tokenData);
           	throw exception;
       	}
	}

	public UserEntity convertToEntity(User user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setIdUser(user.getIdUser());
		userEntity.setUsername(user.getUsername());
		userEntity.setUserPhoto(user.getUserPhoto());
		userEntity.setName(user.getCompleteName());
		userEntity.setRegistrationDate(user.getRegistrationDate());
		userEntity.setBirthDate(user.getBirthDate());
		userEntity.setEmail(user.getEmail());
		userEntity.setLastNameM(user.getLastNameM());
		userEntity.setLastNameP(user.getLastNameP());
		userEntity.setStatusUser(user.getStatusUser());
		userEntity.setProfile(user.getProfile());
		return userEntity;
	}

	public User convertToDomain(UserEntity user) {
		User u = new User();
		u.setAddress(user.getAddress());
		u.setBirthDate(user.getBirthDate());
		u.setEmail(user.getEmail());
		u.setIdUser(user.getIdUser());
		u.setLastNameM(user.getLastNameM());
		u.setLastNameP(user.getLastNameP());
		u.setName(user.getName());
		u.setPassword(user.getPassword());
		u.setProfile(user.getProfile());
		u.setRegistrationDate(user.getRegistrationDate());
		u.setStatusUser(user.getStatusUser());
		u.setUsername(user.getUsername());
		u.setUserPhoto(user.getUserPhoto());
		return u;
	}

	public List<UserEntity> convertToEntity(List<User> users) {
		List<UserEntity> usersEntity = new ArrayList<UserEntity>();
		for (User user : users) {
			usersEntity.add(convertToEntity(user));
		}
		return usersEntity;
	}
}
