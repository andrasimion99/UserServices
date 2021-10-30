package service;

import java.util.Optional;

import dao.UserDao;
import entity.User;

public class AuthenticationServices {
	private UserDao userDao;
	
	public AuthenticationServices() {
		userDao = UserDao.getUserDao();
	}

	public AuthenticationServices(UserDao dao) {
		this.userDao = dao;
	}
	
	private boolean userExistsWithEmail(final String email) {
		return userDao.findByEmail(email).isPresent();
	}

	public User createUser(String email, String password, String name) {
		if (!userExistsWithEmail(email)) {
			return userDao.create( new User(name, password, email));
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
