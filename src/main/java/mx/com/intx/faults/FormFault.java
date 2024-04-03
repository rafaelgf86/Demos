/**
 * mx.com.intx.faults
 */
package mx.com.intx.faults;

/**
 * Clase encargada de manejar los errores de validacion de los modelos para ser registrados
 * <br>
 * <strong>Descripcion:</strong><br>
 * Errores de Usuario: V_USR_001 => [Validacion]_[Usuario]_[Numero incremental ] <br>
 *  <br>
 * "V_USR_000", "The user does not exist" <br>
 * "V_USR_001", "name", "Name is mandatory" <br>
 * "V_USR_002", "name", "Name must be 4 letters at least" <br>
 * "V_USR_003", "name", "Name must not exceed 99 letters" <br>
 * "V_USR_004", "lastNameP", "Last Name is mandatory" <br>
 * "V_USR_005", "lastNameP", "Last Name must be 4 letters at least" <br>
 * "V_USR_006", "lastNameP", "Last Name must not exceed 99 letters" <br>
 * "V_USR_007", "lastNameM", "Last Name is mandatory" <br>
 * "V_USR_008", "lastNameM", "Last Name must be 4 letters at least" <br>
 * "V_USR_009", "lastNameM", "Last Name must not exceed 99 letters" <br>
 * "V_USR_010", "email", "Email is mandatory" <br>
 * "V_USR_011", "email", "Email must be 4 letters at least" <br>
 * "V_USR_012", "email", "Email must not exceed 99 letters" <br>
 * "V_USR_013", "password", "Password is mandatory" <br>
 * "V_USR_014", "password", "Password must be 4 letters at least" <br>
 * "V_USR_015", "password", "Password must not exceed 20 letters" <br>
 * "V_USR_016", "profile", "Profile is mandatory" <br>
 * "V_USR_017", "username", "UserName is mandatory" <br>
 * "V_USR_018", "username", "UserName must be 4 letters at least" <br>
 * "V_USR_019", "username", "UserName must not exceed 50 letters" <br>
 * "V_USR_020", "username", "Username can not have special caracteres" <br>
 * "V_USR_021", "username", "UserName has already been used " <br>
 * "V_USR_022", "username", "The username can not be updated" <br>
 * 
 * 
 * Errores de Contactanos: V_CTU_001 => [Validacion]_[Contactanos]_[Numero incremental] <br>
 *  <br>
 * "V_CTU_000", "Email is mandatory" <br>
 * "V_CTU_001", "email", "It is not a valid email" <br>
 * "V_CTU_002", "comment", "Comment is mandatory" <br>
 * 
 * @author INTX
 */
public class FormFault {

	private String code;
	private String field;
	private String message;
 
	public FormFault(String code, String field, String message) {
		this.code = code;
		this.message = message;
    	this.field = field; 
    }
	
	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}
	
}
