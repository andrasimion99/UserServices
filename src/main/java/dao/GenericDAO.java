package dao;

public interface GenericDAO<T> {
	public T create(T t);

	public T update(T t);

	public T get(Object id);

	public void delete(Object id);

}
