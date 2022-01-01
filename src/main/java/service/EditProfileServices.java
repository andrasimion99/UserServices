package service;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import dao.UserDao;
import entity.User;

public class EditProfileServices {
    @Inject
    private UserDao userDao;

    public EditProfileServices() {
    }

    public EditProfileServices(UserDao userDao){
        this.userDao = userDao;
    }

    public User editEmail(Integer id, String email) throws EntityNotFoundException {
        User user = userDao.get(id);
        user.setEmail(email);
        return userDao.update(user);
    }

    public User editPassword(Integer id, String password) throws EntityNotFoundException {
        User user = userDao.get(id);
        user.setPassword(password);
        return userDao.update(user);
    }

    public User editName(Integer id, String name) throws EntityNotFoundException {
        User user = userDao.get(id);
        user.setName(name);
        return userDao.update(user);
    }
}
