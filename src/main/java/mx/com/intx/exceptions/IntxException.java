/**
 * mx.com.intx.exceptions
 */
package mx.com.intx.exceptions;

/**
 * @author INTX
 * 
 * Base de datos : 1000 - 1999<br>
 * 1000 : Error general<br>
 * 
 * 1100 : Error de conexion<br>
 * 1101 : Conexion inactiva<br>
 * 
 * 1200 : Error de consulta<br>
 * 
 * 1300 : Error de transaccion<br>
 * 
 * -------------------------------------<br>
 * Encriptacion<br>
 * 2000 : Error de certificados de encripcion<br>
 * 
 * 
 * -------------------------------------<br>
 * Token<br>
 * 3000 : Error desencriptacion token<br>
 * 
 * -------------------------------------<br>
 * Resources<br>
 * 4000 : Error al crear copia fisica en el server<br>
 * 
 * -------------------------------------<br>
 * e-Mail<br>
 * 5000 : No hay configuracion de correos<br>
 */
public class IntxException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private int code;
	
	/**
	 * Codigos:
	 * 1000 : Base de datos
	 * @param code
	 * @param message
	 * @param cause
	 */
	public IntxException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
