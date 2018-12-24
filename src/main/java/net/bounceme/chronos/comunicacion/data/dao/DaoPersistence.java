package net.bounceme.chronos.comunicacion.data.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Clase para crear repositorios de forma polimórfica, pasándole un tipo de objeto.
 * El tipo de objeto está determinado por el parámetro T de la clase
 * 
 * @author frederik
 *
 */
public class DaoPersistence<T> {
	private static final String ERROR = "ERROR: ";

    private static final Logger log = LoggerFactory.getLogger(DaoPersistence.class);
	
	@Autowired
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

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.data.dao.DaoPersistence#saveObject(T)
	 */
	public T saveObject(final T object) throws Exception {
		try {
			entityManager.persist(object);
			return object;
		} catch (Exception e) {
			log.error(ERROR, e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.data.dao.DaoPersistence#getObject(java.io.Serializable)
	 */
	public T getObject(final Serializable identifier) {
		return entityManager.find(clazz, identifier);
	}
	
	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.data.dao.DaoPersistence#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Query query = entityManager.createQuery("FROM " + clazz.getName());
		return (List<T>) query.getResultList();
	}
		
	/**
	 * @param identifier
	 */
	public void removeObjectById(final Serializable identifier) throws Exception {
		try {
			Object object = entityManager.find(clazz, identifier);
			entityManager.remove(object);
		} catch (Exception e) {
		    log.error(ERROR, e);
			throw e;
		}
	}
	
	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.data.dao.DaoPersistence#removeObject(java.io.Serializable)
	 */
	public void removeObject(Object object) throws Exception {
		try {
			entityManager.remove(object);
		} catch (Exception e) {
		    log.error(ERROR, e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.data.dao.DaoPersistence#updateObject(T)
	 */
	public void updateObject(final T object) throws Exception {
		try {
			entityManager.merge(object);
		} catch (Exception e) {
		    log.error(ERROR, e);
			throw e;
		}
	}
}