package dao;

import constants.QueryConstants;
import entity.User;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class UserDao {
    @Inject
    JpaDao<User> jpaDao;

    public UserDao() {
    }

    public UserDao(JpaDao<User> jpa){
        jpaDao = jpa;
    }

    public User create(User user) {
        return jpaDao.create(user);
    }

    public User update(User user) {
        return jpaDao.update(user);
    }

    public User get(Object id) throws EntityNotFoundException {
        User user = jpaDao.get(User.class, id);
        if (user != null) {
            return user;
        } else {
            throw new EntityNotFoundException("Unable to find entity.User with id " + id);
        }
    }

    public void delete(Object id) throws EntityNotFoundException {
        jpaDao.delete(User.class, id);
    }

    public Optional<User> findByEmail(String email) {
        try {
            return jpaDao.getByProperty(QueryConstants.FIND_USER_BY_EMAIL, email);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<User> getAllUsers() {
        try {
            return jpaDao.getAll(QueryConstants.GET_ALL_USERS);
        } catch (NoResultException e) {
            return null;
        }
    }
}
