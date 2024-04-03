/**
 * mx.com.intx.repository
 */
package mx.com.intx.repository;

import java.util.List;
import java.util.Map;

import mx.com.intx.exceptions.IntxException;

/**
 * @author INTX
 *
 */
public interface IGenericDao<T> {

	/**
	 * Metodo que se usa para especificar la clase del generics <T>, instanciada, y
	 * poder usarla en los diferentes metodos de hibernate
	 * 
	 * @param clazzToSet
	 */
	void setClazz(Class<T> clazzToSet);

	/**
	 * Metodo que hace un persist del objeto recibido. SE RECOMIENDA SOLO USAR PARA
	 * SALVAR OBJETOS NUEVOS
	 * 
	 * @param model Modelo nuevo a guardar
	 * @throws IntxException 
	 */
	void persist(T model) throws IntxException;

	/**
	 * Metodo que hace un merge el objeto recibido SE RECOMIENDA SOLO USAR PARA
	 * ACTUALIZAR OBJETOS EXISTENTES
	 * 
	 * @param model Modelo a guardar o actualizar
	 * @throws IntxException 
	 */
	T merge(T model) throws IntxException;

	/**
	 * Metodo que elimina el objeto recibido
	 * 
	 * @param model Modelo a eliminar
	 * @throws IntxException 
	 */
	void delete(T model) throws IntxException;

	/**
	 * Metodo que guarda o actualiza la lista de objetos recibida y posteriormente
	 * elimina la segunda lista de objetos recibida
	 * 
	 * @param listToSave   Lista de modelos a guardar o actualizar
	 * @param listToDelete Lista de modelos a eliminar
	 * @throws IntxException 
	 */
	void mergeThenDelete(List<Object> listToSave, List<Object> listToDelete) throws IntxException;

	/**
	 * Metodo que elimina la primera lista de objetos y posteriormente guarda o
	 * actualiza la segunda lista de objetos recibida
	 * 
	 * @param listToDelete Lista de modelos a eliminar
	 * @param listToSave   Lista de modelos a guardar o actualizar
	 * @throws IntxException 
	 */
	void deleteThenMerge(List<Object> listToDelete, List<Object> listToSave) throws IntxException;

	/**
	 * Metodo que realiza una consulta paginada con una condicion LIKE
	 * 
	 * @param offset         Numero que indica la posicion inicial de los registros
	 *                       a devolver
	 * @param limit          Numero que indica la cantidad total de registros que se
	 *                       devolveran
	 * @param orderBy        Numero que indica el campo por el cual ocurrira el
	 *                       ordenamiento y este debera de coincidir con uno de los
	 *                       valores del mapa orderByProperty
	 * @param order          Cadena que indica si el ordenamiento sera ascendente o
	 *                       descendente
	 * @param properties     Mapa con todos los campos posibles por los cuales puede
	 *                       ocurrir un ordenamiento
	 * @param searchProperty Cadena que indica el campo por el cual se hara el
	 *                       filtro LIKE de la consulta
	 * @param searchValue    Cadena que indica el valor por el cual se buscara en el
	 *                       filtro LIKE de la consulta
	 * @return Consulta paginada
	 * @throws IntxException 
	 */
	List<T> paginatedSearch(int offset, int limit, int orderBy, String order, Map<Integer, String> properties,
			String searchProperty, String searchValue) throws IntxException;

	/**
	 * Metodo que devuelve el conteo de todos los registros que cumplen con una
	 * condicion LIKE con base en el campo y valor recibido
	 * 
	 * @param searchProperty Cadena que indica el campo por el cual se hara el
	 *                       filtro LIKE de la consulta
	 * @param searchValue    Cadena que indica el valor por el cual se buscara en el
	 *                       filtro LIKE de la consulta
	 * @return Total de registros encontrados
	 * @throws IntxException 
	 */
	long paginatedSearchTotal(String searchProperty, String searchValue) throws IntxException;

	/**
	 * Metodo que consulta todos los registros del modelo recibido
	 * 
	 * @return Listado con todos los registros existentes
	 * @throws IntxException 
	 */
	List<T> findAll() throws IntxException;

	/**
	 * Metodo que obtiene un unico registro con base en el id recibido
	 * 
	 * @param idValue Cadena con el valor por el cual se hara de la consulta
	 * @return
	 * @throws IntxException 
	 */
	T getById(Object idValue) throws IntxException;

	/**
	 * Metodo que obtiene una lista de registros con base en el nombre de la propiedad
	 * recibida y su valor. Genera una condicion de igualdad
	 * 
	 * @param nameProperty  Cadena que indica el campo identificador
	 * @param valueProperty Cadena con el valor por el cual se hara de la consulta
	 * @return
	 * @throws IntxException 
	 */
	List<T> getByPropertyEQ(String nameProperty, Object valueProperty) throws IntxException;
}
