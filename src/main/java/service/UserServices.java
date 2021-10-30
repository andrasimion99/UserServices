package service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import dao.UserDao;
import entity.User;

public class UserServices {
	private UserDao userDao;

	public UserServices() {
		userDao = UserDao.getUserDao();
	}

	public UserServices(UserDao dao) {
		this.userDao = dao;
	}

	public User getById(Integer id) throws EntityNotFoundException{
		return userDao.get(id);
	}
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	public void deleteUser(Integer id) throws EntityNotFoundException{
		userDao.delete(id);
	}
}
