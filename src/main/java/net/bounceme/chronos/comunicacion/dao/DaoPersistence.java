package net.bounceme.chronos.comunicacion.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import net.bounceme.chronos.comunicacion.exceptions.DataException;


/**
 * Clase para crear repositorios de forma polimórfica, pasándole un tipo de objeto.
 * El tipo de objeto está determinado por el parámetro T de la clase
 * 
 * @author frederik
 *
 */
public class DaoPersistence<T> {
	Logger log = Logger.getLogger(DaoPersistence.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Class<T> clazz;

	/**
	 * Crea el repositorio pasándole la clase del tipo de objeto
	 * 
	 * @param clazz
	 */
	public DaoPersistence(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	/**
	 * Crea un nuevo objeto en el entorno de persistencia
	 * 
	 * @param object
	 * @return
	 * @throws DataException
	 */
	public T saveObject(final T object) throws DataException {
		try {
			entityManager.persist(object);
			return (T) object;
		} catch (Exception e) {
			log.error(e);
			throw new DataException(e);
		}
	}

	/**
	 * Obtiene un objeto del entorno de persistencia 
	 * 
	 * @param identifier identificador del objeto
	 * @return objeto
	 */
	public T getObject(final Serializable identifier) {
		return entityManager.find(clazz, identifier);
	}
		
	/**
	 * Borra un objeto del entorno de persistencia
	 * 
	 * @param identifier
	 * @throws DataException
	 */
	public void removeObject(final Serializable identifier) throws DataException {
		try {
			Object object = entityManager.find(clazz, identifier);
			entityManager.remove(object);
		} catch (Exception e) {
			log.error(e);
			throw new DataException(e);
		}
	}

	/**
	 * Actualiza un objeto del entorno de persistencia
	 * 
	 * @param object objeto modificado
	 * @throws DataException
	 */
	public void updateObject(final T object) throws DataException {
		try {
			entityManager.merge(object);
		} catch (Exception e) {
			log.error(e);
			throw new DataException(e);
		}
	}
}