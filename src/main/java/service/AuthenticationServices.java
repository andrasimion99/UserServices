package service;

import java.util.Optional;

import dao.UserDao;
import entity.User;

import javax.inject.Inject;

public class AuthenticationServices {
    @Inject
    private UserDao userDao;

    public AuthenticationServices() {
    }

    public AuthenticationServices(UserDao dao) {
        this.userDao = dao;
    }

    public boolean userExistsWithEmail(final String email) {
        return userDao.findByEmail(email).isPresent();
    }

    public User createUser(String email, String password, String name) {
        if (!userExistsWithEmail(email)) {
            return userDao.create(new User(name, password, email));
        }
        return null;
    }

    public User login(String email, String password) {
        Optional<User> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return password.equals(user.getPassword()) ? user : null;
        }
        return null;
    }

}
