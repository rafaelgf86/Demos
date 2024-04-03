package mx.com.intx.web;

import java.util.HashMap;
import java.util.Map;

import mx.com.intx.exceptions.FormException;
import mx.com.intx.faults.FormFault;

/**
 * Clase que contiene los valores por defecto para el paginado
 * 
 * @author INTX
 *
 */
public class DefaultValues {

	public final static String OFFSET = "0";
	public final static String LIMIT = "5";
	public final static String ORDER_BY = "1";
	public final static String ORDER = " ASC ";

	public Map<String, Object> getError(Exception e) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", e.getMessage());
		return resultMap;
	}

	public Map<String, Object> getFormErrors(FormException fe) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (fe.getFaultInfo() != null && fe.getFaultInfo().size() > 0) {
			// Por cada error de formulario
			for (FormFault formFault : fe.getFaultInfo()) {
				resultMap.put(formFault.getField(), formFault.getCode() + "|" + formFault.getMessage());
			}
		}
		return resultMap;
	}

}
