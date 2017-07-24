package net.bounceme.chronos.comunicacion.dao;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.utils.Constantes;

/**
 * Clase de gestión de las consultas HQL al repositorio.
 * Las consultas con nombre están definidas en el archivo
 * orm.xml
 * 
 * @author frederik
 *
 */
@Repository(DaoQueries.NAME)
public class DaoQueries {
	
	public static final String NAME = "daoQueries"; 
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Ejecuta una consulta determinada por su nombre
	 * 
	 * @param queryName el nombre de la consulta
	 * @param cacheable indica si la consulta se va a cachear o no
	 * @return resultados 
	 */
	@SuppressWarnings("rawtypes")
	public Collection executeNamedQuery(String queryName, Boolean cacheable) {
		Query query = entityManager.createNamedQuery(queryName);
		query.setHint(Constantes.HINT_CACHEABLE, cacheable);
		return query.getResultList();
	}
	
	/**
	 * Ejecuta una consulta determinada por su nombre, pasándole los parámetros que requiera
     * 
     * @param queryName el nombre de la consulta
	 * @param parameters Mapa con los parámetros
	 * @param cacheable indica si la consulta se va a cachear o no
	 * @return resultados
	 */
	@SuppressWarnings("rawtypes")
	public Collection executeNamedQuery(String queryName, Map<String, Object> parameters, Boolean cacheable) {
		Query query = entityManager.createNamedQuery(queryName);
		query.setHint(Constantes.HINT_CACHEABLE, cacheable);
		setParameters(query, parameters);
		return query.getResultList();
	}
	
	/**
	 * Método de conveniencia para el paso de parámetros a las consultas
	 * 
	 * @param query
	 * @param parameters
	 */
	private void setParameters(Query query, Map<String, Object> parameters) {
		for (String name : parameters.keySet()) {
			query.setParameter(name, parameters.get(name));
		}
	}

}
