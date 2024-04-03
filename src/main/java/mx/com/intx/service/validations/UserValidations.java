/**
 * mx.com.intx.service.validations
 */
package mx.com.intx.service.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import mx.com.intx.domain.User;
import mx.com.intx.exceptions.FormException;
import mx.com.intx.faults.FormFault;
import mx.com.intx.repository.IGenericDao;
import mx.com.intx.service.Manager;
import mx.com.intx.utils.Utilities;

/**
 * @author INTX
 *
 *
 * Last code
 *	1023 : 
 */
public class UserValidations extends Manager {

	protected IGenericDao<User> daoUser;

	@Autowired
	public void setDaoUser(IGenericDao<User> daoUser) {
		this.daoUser = daoUser;
		daoUser.setClazz(User.class);
	}
	
	public void applyUpdValidations( User user, User userDb ) throws Exception {
		List<FormFault> formFaults = new ArrayList<FormFault>();
		
		if (userDb == null) {
			formFaults.add( new FormFault("V_USR_000", "idUser", "The user does not exist") );
		} else {
			if (!userDb.getUsername().equals(userDb.getUsername())) {
				formFaults.add( new FormFault("V_USR_022", "username", "The username can not be updated") );
			}
				
			formFaults.addAll(applyCommonValidations(user));
			
		}
		
		// En caso de que haya errores de formulario lanzar en la excepción
		if ( formFaults.size() > 0 ) {			
			throw new FormException("Form Exception", formFaults);
		}
	}
	
	public void applyAddValidations( User user ) throws Exception {
		List<FormFault> formFaults = new ArrayList<FormFault>();
		
		formFaults.addAll(applyCommonValidations(user));
		
		
		if ( user.getPassword() == null)
			formFaults.add( new FormFault("V_USR_013", "password", "Password is mandatory") );
		else if ( user.getPassword().length() < 4 )
			formFaults.add( new FormFault("V_USR_014", "password", "Password must be 4 letters at least") );
		else if ( user.getPassword().length() > 20 )
			formFaults.add( new FormFault("V_USR_015", "password", "Password must not exceed 20 letters") );

		if ( user.getUsername() == null)
			formFaults.add( new FormFault("V_USR_017", "username", "UserName is mandatory") );
		else if ( user.getUsername().length() < 4 )
			formFaults.add( new FormFault("V_USR_018", "username", "UserName must be 4 letters at least") );
		else if ( user.getUsername().length() > 50 )
			formFaults.add( new FormFault("V_USR_019", "username", "UserName must not exceed 50 letters") );
		else if (  !Utilities.validatePattern( user.getUsername().trim(), "^[a-zA-Z0-9_]+$") ) 
			formFaults.add( new FormFault("V_USR_020", "username", "Username can not have special caracteres") );
		
		// Validar si el username no se ha ocupado
		List<User> users = this.daoUser.getByPropertyEQ("username", user.getUsername());
		if ( users != null && !users.isEmpty())
			formFaults.add( new FormFault("V_USR_021", "username", "UserName has already been used ") );
			
		// En caso de que haya errores de formulario lanzar en la excepción
		if ( formFaults.size() > 0 ) {			
			throw new FormException("Form Exception", formFaults);
		}
	}
	
	public List<FormFault> applyCommonValidations( User user ) {
		List<FormFault> formFaults = new ArrayList<FormFault>();
		// Validaciones
		if ( user.getUsername() == null)
			formFaults.add( new FormFault("V_USR_001", "name", "Name is mandatory") );
		else if ( user.getName().length() < 4 )
			formFaults.add( new FormFault("V_USR_002", "name", "Name must be 4 letters at least") );
		else if ( user.getName().length() > 99 )
			formFaults.add( new FormFault("V_USR_003", "name", "Name must not exceed 99 letters") );	

		if ( user.getLastNameP() == null)
			formFaults.add( new FormFault("V_USR_004", "lastNameP", "Last Name is mandatory") );
		else if ( user.getLastNameP().length() < 4 )
			formFaults.add( new FormFault("V_USR_005", "lastNameP", "Last Name must be 4 letters at least") );
		else if ( user.getLastNameP().length() > 99 )
			formFaults.add( new FormFault("V_USR_006", "lastNameP", "Last Name must not exceed 99 letters") );

		if ( user.getLastNameM() == null)
			formFaults.add( new FormFault("V_USR_007", "lastNameM", "Last Name is mandatory") );
		else if ( user.getLastNameM().length() < 4 )
			formFaults.add( new FormFault("V_USR_008", "lastNameM", "Last Name must be 4 letters at least") );
		else if ( user.getLastNameM().length() > 99 )
			formFaults.add( new FormFault("V_USR_009", "lastNameM", "Last Name must not exceed 99 letters") );
		
		if ( user.getEmail() == null)
			formFaults.add( new FormFault("V_USR_010", "email", "Email is mandatory") );
		else if ( user.getEmail().length() < 4 )
			formFaults.add( new FormFault("V_USR_011", "email", "Email must be 4 letters at least") );
		else if ( user.getEmail().length() > 99 )
			formFaults.add( new FormFault("V_USR_012", "email", "Email must not exceed 99 letters") );
		

		if ( user.getProfile() == null || user.getProfile().getIdProfile() == 0)
			formFaults.add( new FormFault("V_USR_016", "profile", "Profile is mandatory") );
			
		
		return formFaults;
	}
}
