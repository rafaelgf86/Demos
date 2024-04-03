/**
 * mx.com.intx.exceptions
 */
package mx.com.intx.exceptions;

import java.util.ArrayList;
import java.util.List;

import mx.com.intx.faults.FormFault;

/**
 * @author INTX
 *
 */
public class FormException extends Exception {

	private static final long serialVersionUID = 1L;
	private List<FormFault> innovatixFaults; 

    public FormException(String message, List<FormFault> innovatixFaults){ 
    	super(message);
    	this.innovatixFaults = innovatixFaults;
    }   
    
    public FormException(String message, List<FormFault> innovatixFaults, Throwable cause){ 
    	super(message, cause);
    	this.innovatixFaults = innovatixFaults;
    }     
    
    public FormException(String message, FormFault innovatixFault){ 
    	super(message);
    	innovatixFaults = new ArrayList<FormFault>();
    	this.innovatixFaults.add(innovatixFault);
    }  
     
    public List<FormFault> getFaultInfo(){
    	return this.innovatixFaults;
    }
}
