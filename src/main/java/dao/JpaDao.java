package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class JpaDao<T> implements GenericDAO<T> {
    @PersistenceContext(name = "users")
    EntityManager entityManager;

    public JpaDao() {
    }

    public JpaDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T create(T t) {
        entityManager.persist(t);
        entityManager.flush();
        entityManager.refresh(t);
        return t;
    }

    @Override
    public T update(T t) throws EntityNotFoundException {
        return entityManager.merge(t);
    }

    @Override
    public T get(Class<T> entityClass, Object id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public void delete(Class<T> entityClass, Object id) throws EntityNotFoundException {
        Object reference = entityManager.getReference(entityClass, id);
        entityManager.remove(reference);
    }

    @SuppressWarnings("unchecked")
    public Optional<T> getByProperty(String query, String prop) {
        return Optional.of((T) entityManager.createNamedQuery(query)
                .setParameter(1, prop).setMaxResults(1).getSingleResult());
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(String query) {
        return entityManager.createNamedQuery(query).getResultList();
    }
}
