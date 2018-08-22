package net.bounceme.chronos.comunicacion.data.dao;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import net.bounceme.chronos.comunicacion.utils.Constantes;

/**
 * Clase de gestión de las consultas HQL al repositorio.
 * Las consultas con nombre están definidas en el archivo
 * orm.xml
 * 
 * @author frederik
 *
 */
public class DaoQueries {
	
	public static final String NAME = "daoQueries"; 
	
	private static final Logger log = Logger.getLogger(DaoQueries.class);
	
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
	public Collection executeNamedQuery(String queryName) {
		return executeNamedQuery(queryName, Boolean.TRUE);
	}

	
	
	/**
	 * Ejecuta una consulta determinada por su nombre
	 * 
	 * @param queryName el nombre de la consulta
	 * @param cacheable indica si la consulta se va a cachear o no
	 * @return resultados 
	 */
	@SuppressWarnings("rawtypes")
	public Collection executeNamedQuery(String queryName, Boolean cacheable) {
		return executeNamedQuery(queryName, null, cacheable);
	}
	
	/**
	 * @param queryName
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Collection executeNamedQuery(String queryName, Map<String, Object> parameters) {
		return executeNamedQuery(queryName, parameters, Boolean.TRUE);
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
		Query query = createQuery(queryName, cacheable);
		if (parameters!=null) {
			setParameters(query, parameters);
		}
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
	public Optional executeScalarNamedQuery(String queryName, Map<String, Object> parameters, Boolean cacheable) {
		try {
			Query query = createQuery(queryName, cacheable);
			setParameters(query, parameters);
			return Optional.ofNullable(query.getSingleResult());
		} catch (NoResultException e) {
			log.warn("No se ha encontrado la entidad");
			return Optional.empty();
		}
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
	
	/**
	 * @param queryName
	 * @param cacheable
	 * @return
	 */
	private Query createQuery(String queryName, Boolean cacheable) {
		Query query = entityManager.createNamedQuery(queryName);
		query.setHint(Constantes.HINT_CACHEABLE, cacheable);
		return query;
	}

}
