package service;

import dao.UserDao;
import entity.User;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public class UserServices {

    @Inject
    private UserDao userDao;

    public UserServices() {
    }

    public UserServices(UserDao dao) {
        this.userDao = dao;
    }

    public User getById(Integer id) throws EntityNotFoundException {
        return userDao.get(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void deleteUser(Integer id) throws EntityNotFoundException {
        userDao.delete(id);
    }
}
