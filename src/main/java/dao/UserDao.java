package dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import constants.QueryConstants;
import constants.GenericConstants;
import constants.ParametersConstants;
import entity.User;

public class UserDao implements GenericDAO<User> {
	private JpaDao<User> jpaDao;
	private EntityManagerFactory entityManagerFactory;
	static public UserDao userDao = new UserDao();

	private UserDao() {
		entityManagerFactory = Persistence.createEntityManagerFactory(GenericConstants.DB);
		jpaDao = new JpaDao<User>(entityManagerFactory);
		System.out.println(this.getClass());
	}

	public static UserDao getUserDao() {
		return userDao;
	}

	@Override
	public User create(User user) {
		return jpaDao.create(user);
	}

	@Override
	public User update(User user) {
		return jpaDao.update(user);
	}

	@Override
	public User get(Object id) throws EntityNotFoundException{
		User user = jpaDao.find(User.class, id);
		if (user!=null) {
			return user;
		}else {
			throw new EntityNotFoundException("Unable to find entity.User with id " + id);
		}
	}

	@Override
	public void delete(Object id) throws EntityNotFoundException{
		jpaDao.delete(User.class, id);

	}

	public Optional<User> findByEmail(String email) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			return Optional.of((User) entityManager.createQuery(QueryConstants.FIND_USER_BY_EMAIL)
					.setParameter(ParametersConstants.EMAIL, email).setMaxResults(1).getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		} finally {
			entityManager.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			return  entityManager.createQuery(QueryConstants.GET_ALL_USERS).getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			entityManager.close();
		}
	}
}
