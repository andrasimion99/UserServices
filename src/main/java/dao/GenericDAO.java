package dao;

public interface GenericDAO<T> {
	T create(T t);

	T update(T t);

	T get(Class<T> entityClass, Object id);

	void delete(Class<T> entityClass, Object id);

}
