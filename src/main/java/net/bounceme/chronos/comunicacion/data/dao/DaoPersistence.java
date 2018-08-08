package net.bounceme.chronos.comunicacion.data.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Clase para crear repositorios de forma polimórfica, pasándole un tipo de objeto.
 * El tipo de objeto está determinado por el parámetro T de la clase
 * 
 * @author frederik
 *
 */
public class DaoPersistence<T> {
	
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
	public T saveObject(final T object) {
		entityManager.persist(object);
		return object;
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
	public void removeObjectById(final Serializable identifier) {
		Object object = entityManager.find(clazz, identifier);
		entityManager.remove(object);
	}
	
	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.data.dao.DaoPersistence#removeObject(java.io.Serializable)
	 */
	public void removeObject(Object object) {
		entityManager.remove(object);
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.data.dao.DaoPersistence#updateObject(T)
	 */
	public void updateObject(final T object) {
		entityManager.merge(object);
	}
}