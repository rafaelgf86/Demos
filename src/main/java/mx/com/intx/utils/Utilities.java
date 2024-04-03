/**
 * mx.com.intx.utils
 */
package mx.com.intx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author INTX
 *
 */
public class Utilities {

	/**
	 * Metodo que valida una cadena con el patron indicado, si la validacion arroja un
	 * resultado negativo, se volvera a intentar pero reemplazando los caracteres especiales
	 * @param value 
	 * @param pattern
	 * @return
	 */
	public static boolean validatePattern(String value,String pattern) {
		return validatePattern(value, pattern, true);
	}
	
	/**
	 * Metodo que valida una cadena con el patron indicado
	 * @param value 
	 * @param pattern
	 * @param applySecondVal Bandera que indica que se realizara una segunda validacion si la 
	 * primera falla, reemplazando los caracteres especiales
	 * @return
	 */
	public static boolean validatePattern(String value, String pattern, boolean applySecondVal){   
		boolean success = false;
		
		if ( value == null)
			return success;
		
		try{
			Pattern p = Pattern.compile(pattern);     
			Matcher m = p.matcher(value);  
			success =  m.matches();
			
			// Si fallo intentar remover caracteres especiales ANSI
			if ( !success && applySecondVal ) {
				m = p.matcher( value.replaceAll("[\\x00-\\x20\\xA0]+$", "") );  
				success =  m.matches();
			}
			
		}catch(Exception e){
			LoggerIntx.printError("Utilities", e);
		}
		
		return success;
	}
	
	/**
	 * Función que removera todos los caracteres invalidos para un nombre de archivo y los reemplzara por un 
	 * guion bajo
	 * @param fileName
	 * @return
	 */
	public static String getValidFileName(String fileName) {
		return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
	}
	
	/**
	 * Funcion que obtiene el tamaño en bytes de una cadena en base64
	 * @param in
	 * @return
	 */
	public static long countBase64Size(String in) {
		long count = 0;
		long pad = 0;
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '=')
				pad++;
			if (!Character.isWhitespace(c))
				count++;
		}
		return (count * 3 / 4) - pad;
	}
}
