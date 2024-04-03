package mx.com.intx.responses;

import java.util.HashMap;
import java.util.Map;

public class ResponseWS {

	/*********** Attributes ************/
	private boolean success;
	private Map<String, Object> errors = new HashMap<String, Object>();
	private boolean messagesCodes = false;
	
	/*********** Getters and Setters ************/
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Map<String, Object> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, Object> errors) {
		this.errors = errors;
	}
	public boolean isMessagesCodes() {
		return messagesCodes;
	}
	public void setMessagesCodes(boolean messagesCodes) {
		this.messagesCodes = messagesCodes;
	}
	

	
}
