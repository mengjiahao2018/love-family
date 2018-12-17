package com.love.family.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.dao.user.UserDao;
import com.love.family.entity.user.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public User findById(Integer id) {
		return userDao.findById(id);
	}

	@Override
	public User save(String name) {
		return userDao.save(new User(name));
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
}
