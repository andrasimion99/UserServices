package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

public class JpaDao<T> {
	private EntityManagerFactory entityManagerFactory;

	public JpaDao(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public T create(T t) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.flush();
		entityManager.refresh(t);
		entityManager.getTransaction().commit();
		entityManager.close();
		return t;
	}

	public T update(T t) throws EntityNotFoundException{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		t = entityManager.merge(t);
		entityManager.getTransaction().commit();
		entityManager.close();
		return t;
	}

	public T find(Class<T> type, Object id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		T entity = entityManager.find(type, id);
		entityManager.close();
		return entity;
	}

	public void delete(Class<T> type, Object id) throws EntityNotFoundException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Object reference = entityManager.getReference(type, id);
		entityManager.remove(reference);
		entityManager.getTransaction().commit();	
		entityManager.close();
	}

}
