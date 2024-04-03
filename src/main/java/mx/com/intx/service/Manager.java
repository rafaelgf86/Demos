package mx.com.intx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.ServletContext;
import mx.com.intx.responses.SearchResponse;

/**
 * Clase encargada de armar las respuestas de tipo SearchResponse 
 * @author INTX
 *
 */
public class Manager {
	
	// pass por defecto, que se regresa en algunos campos sensibles
	protected final String DEFAULT_PHRASE = "-1-1-1-1-1-1-1-1-1-1-1-1-1"; 
		
	@Autowired
    public ILogManager logManager;
	
	@Autowired
    private IJWTSecurityManager jwtSecurityManager;
	
	protected static ServletContext servletContext;
	
	/***********************************************************************
	 * Autowired ServletContext
	 * *********************************************************************/

	/**
	 * Note: the use of class name because that field should be static thus shared by all instances.
	 * @param servletContext
	 */
	@Autowired
	public void setServletContext(ServletContext servletContext) {
		Manager.servletContext = servletContext;
	}
	
	public static ServletContext getServletContext() {
		return servletContext;
	}
	
	/**
	 * Método que regresa las respuestas en formato SearchResponse para paginado
	 * @param offset
	 * @param limit
	 * @param objects
	 * @param totalObjects
	 * @return
	 */
	public SearchResponse getSearchResponse ( int offset, int limit, List<?> objects, long totalObjects ) {	
        SearchResponse searchResponse = new SearchResponse(); 
        searchResponse.setTotal(totalObjects);
        searchResponse.setOffset(offset);
        searchResponse.setLimit(limit);        
		if ( objects == null )
			return searchResponse;		
        searchResponse.setObjects(objects);
        searchResponse.setTotalPages( (totalObjects % limit)==0 ? Math.round(totalObjects / (float)limit) : Math.round(totalObjects / (float)limit) +1  );
        return searchResponse;
	}
	
	public String getUsername( String token ) {
		return this.jwtSecurityManager.getUsername( token );
	}
	
	public Long getIdUser( String token ) {
		return this.jwtSecurityManager.getIdUser( token );
	}

	public String encrypt( String cadena) throws Exception {
		return this.jwtSecurityManager.encrypt( cadena );
	}
	
	public String desencrypt( String cadena) throws Exception {
		return this.jwtSecurityManager.desencrypt( cadena );
	}
	

}
