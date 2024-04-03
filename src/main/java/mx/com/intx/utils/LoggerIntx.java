package mx.com.intx.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoggerIntx {

	private static Logger logger;

	static {
		try {
			// el paquete de la clase debe conincidir con la propiedad de log4j logger.app.name
			logger = LogManager.getLogger(LoggerIntx.class.getName());
		} catch (Exception ex) {
//			ex.printStackTrace();
		}
	}

	/**
	 * Imprime un mensaje de tipo INFO
	 * @param message :Mensaje a imprimr
	 */
	public static void printLine(String message) {
		logger.info(message);
	}

	/**
	 * Imprime un mensaje de tipo ERROR
	 * @param message :Mensaje a imprimir
	 */
	public static void printError(String message) {
		logger.error(message);
	}
	
	/**
	 * Imprime el stack trace como un mensaje de tipo ERROR
	 * @param clase :Clase donde ocurrio el error
	 * @param ex :StackTrace
	 */
	public static void printError(Object clase, Throwable ex) {
		logger.error(clase, ex);
		
	}
	   
}
