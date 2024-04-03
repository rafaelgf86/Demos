/**
 * mx.com.intx.service
 */
package mx.com.intx.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.stereotype.Service;

import mx.com.intx.domain.LogType;
import mx.com.intx.domain.Resource;
import mx.com.intx.domain.ResourceType;
import mx.com.intx.entities.TokenData;
import mx.com.intx.exceptions.IntxException;
import mx.com.intx.utils.LoggerIntx;

/**
 * @author INTX
 *
 */
@Service
public class ResourceService extends Manager implements IResourceManager {
	
	public static final long IMAGE    = 1L; 
	public static final long VIDEO    = 2L; 
	public static final long DOCUMENT = 3L; 
    
    // private static String FILE_NAME = "files";
    private static String IMAGES_NAME = "images";
    private static String DOCUMENTS_NAME = "documents";
    private static String VIDEO_NAME = "videos";
    
    

	@Override
	public void uploadImage(Resource resource, TokenData tokenData) throws Exception {
		resource.setResourceType( new ResourceType(IMAGE) );
		this.upload(resource, IResourceManager.ENTERPRISE_PATH, IMAGES_NAME, tokenData);
	}

	@Override
	public void uploadDocument(Resource resource, TokenData tokenData) throws Exception {
		resource.setResourceType( new ResourceType(DOCUMENT) );
		this.upload(resource, IResourceManager.ENTERPRISE_PATH, DOCUMENTS_NAME, tokenData);
	}

	@Override
	public void uploadVideo(Resource resource, TokenData tokenData) throws Exception {
		resource.setResourceType( new ResourceType(VIDEO) );
		this.upload(resource, IResourceManager.ENTERPRISE_PATH, VIDEO_NAME, tokenData);
	}

	@Override
	public void deleteLocalResource(Resource resource) throws Exception {
		try {
			File fileToDel = new File(servletContext.getRealPath(resource.getFilePath()));
			System.out.println("tratando de eliminar: " + fileToDel.getAbsolutePath());
			if (fileToDel.exists()) {
				if (!fileToDel.delete()) {
					throw new Exception("No se pudo eliminar el Resource");
				}
			}			
		} catch (Exception e) {
			LoggerIntx.printError("Error al eliminar el Resource: " + resource, e);
		}
	}
	
	
	/**
	 * Función que carga un Resource al servidor, ademas de setear
	 * algunas propiedades del objeto Resource con base en los parametros recibidos.
	 * 
	 * @param resource  Resource a actualizar y a cargar en el servidor
	 * @param path      Parte inicial del path relativo al server donde se copiara
	 *                  el archivo recibido
	 * @param directoryName
	 * @param tokenData Objeto con información de sesión
	 * @throws IntxException 
	 * @throws Exception Excepción al cargar el archivo
	 */
	private void upload(Resource resource, String path, String directoryName, TokenData tokenData) throws IntxException {
		try {
			// Asignar usuario
			resource.setUsername(tokenData.getUsername());
			resource.setRegistrationDate(LocalDateTime.now());
			
			// Obtener ruta absoluta de la carpeta recivda
			String relativeWebPath = path;
			String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
			
			long currentMillis = System.currentTimeMillis();
			
			// Revisar si el directorio (carpeta + files) existe, si no crearlo
			File directory = new File(absoluteDiskPath + "/" + directoryName);
			if (!directory.exists())
				directory.mkdirs();
			
			// Ruta final donde se creará el arhivo, incluir el nombre
			String finalPath = directory.getAbsolutePath() + "/" + currentMillis + "_" + resource.getFileName();
			
			if (resource.getFileData().lastIndexOf(",") != -1) {
				String[] sp = resource.getFileData().split(",");
				resource.setFileMime(sp[0]);
				resource.setFileData(sp[1]);
			}
			
			// Obtener arreglo de bytes
			byte[] data = Base64.getDecoder().decode(resource.getFileData());
			// Almacenar el archivo
			try (OutputStream stream = new FileOutputStream(finalPath)) {
				stream.write(data);
			}
			// Asignar el path al recurso
			resource.setFilePath(path + directoryName + "/" + currentMillis + "_" + resource.getFileName());
			resource.setFileSize(new File(resource.getFilePath()).length());
		} catch (FileNotFoundException e) {
			super.logManager.addErrorLog(LogType.UPLOAD, "The resource could not be written on the specified path", getClass().getCanonicalName(), e, tokenData);
			throw new IntxException(4000, "The resource could not be written on the specified path", e);
		} catch (IOException e) {
			super.logManager.addErrorLog(LogType.UPLOAD, "The resource could not be read/written", getClass().getCanonicalName(), e, tokenData);
			throw new IntxException(4000, "The resource could not be read/written", e);
		}
	}

}
