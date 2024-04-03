package mx.com.intx.service;

import mx.com.intx.entities.TokenData;
import mx.com.intx.responses.ResponseWS;

import java.io.Serializable;
import java.util.Map;

public interface IJWTSecurityManager extends Serializable {
	
	Map<String, Object> createJWT(String username, String password)  throws Exception;
	Map<String, Object> refreshToken(String oldToken) throws Exception;
	boolean validateRequest (String token, ResponseWS response );
	boolean validateRequest (String token, long idSection, ResponseWS response );
	String getUsername(String token);
	Long getIdUser(String token);
	// Encriptar y desencriptar
	String encrypt(String toEncrypt) throws Exception;
	String desencrypt(String toDesencrypt) throws Exception;
	Long getIdProfile(String token);
	TokenData getTokenData(String token) throws Exception;
	
}
