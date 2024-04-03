/**
 * mx.com.intx.web
 */
package mx.com.intx.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.intx.entities.Asentamiento;
import mx.com.intx.entities.Estado;
import mx.com.intx.entities.Municipio;
import mx.com.intx.responses.ObjectResponse;
import mx.com.intx.service.ISepomexManager;
import mx.com.intx.utils.LoggerIntx;

/**
 * @author INTX
 *
 */
@RestController
public class SepomexWSController extends DefaultValues {

	/*******************************************
     * Injections.
     * *****************************************/ 
    @Autowired
    private ISepomexManager sepomexManager;
    
    /*******************************************
     * Methods.
     * *****************************************/
    
    /***
     * Método encargado de obtener los estados del catálogo de SEPOMEX
     * La uri es con GET hacia   : http://localhost:8080/InnovatixControl/sepomex/estados
     * */
    @GetMapping(value="/sepomex/estados", headers="Accept=*/*",produces = "application/json")
    public ResponseEntity<ObjectResponse> getEstados(
    		){
    	// Inicializar respuesta
    	ObjectResponse objectResponse = new ObjectResponse();
		try {
			
			List<Estado> list = this.sepomexManager.getEstadosSepomex();
			
			// Guardar la lista de la búsqueda
			objectResponse.setObject(list);
	       
	        // Regresar el resultado al cliente 
			objectResponse.setSuccess(true);
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }	
    }
    
    /***
     * Método encargado de obtener los municipios del catálogo de SEPOMEX
     * La uri es con GET hacia   : http://localhost:8080/InnovatixControl/sepomex/municipios
     * @RequestParam cEstado      : Código de Estado
     * */
    @GetMapping(value="/sepomex/municipios", headers="Accept=*/*",produces = "application/json")
    public ResponseEntity<ObjectResponse> getMunicipios(
    			@RequestParam(value = "cEstado", required = true) String cEstado
    		){
    	// Inicializar respuesta
    	ObjectResponse objectResponse = new ObjectResponse();
		try {
			List<Municipio> list = this.sepomexManager.getMunicipiosSepomex(cEstado);
			
			// Guardar la lista de la búsqueda
			objectResponse.setObject(list);
	       
	        // Regresar el resultado al cliente 
			objectResponse.setSuccess(true);
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
        	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }	
    }
    
    /*
    ***
    * Método encargado de obtener los asentamientos del catálogo de SEPOMEX
    * La uri es con GET hacia   : http://localhost:8080/InnovatixControl/sepomex/asentamientos
    * @RequestParam cEstado      : Código de Estado
    * @RequestParam cMnpio      : Código de Municipio
    * */
   @GetMapping(value="/sepomex/asentamientos", headers="Accept=*/*",produces = "application/json")
   public ResponseEntity<ObjectResponse> getAsentamientos(
   			@RequestParam(value = "cEstado", required = true) String cEstado,
   			@RequestParam(value = "cMnpio", required = true) String cMnpio
   		){
   	// Inicializar respuesta
	   ObjectResponse objectResponse = new ObjectResponse();
		try {
			List<Asentamiento> list = this.sepomexManager.getAsentamientosSepomex(cEstado, cMnpio);
			
			// Guardar la lista de la búsqueda
			objectResponse.setObject(list);
	       
	        // Regresar el resultado al cliente 
			objectResponse.setSuccess(true);
	        return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.OK);
		} catch(Exception e) {
			LoggerIntx.printError(this, e);
			objectResponse.setErrors( super.getError(e) );
       	return new ResponseEntity<ObjectResponse>(objectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
       }	
   }
  
}
