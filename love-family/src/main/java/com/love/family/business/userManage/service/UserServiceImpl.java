package com.love.family.business.userManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.love.family.business.userManage.dao.UserDao;
import com.love.family.business.userManage.entity.UserModel;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;

	@Override
	public Page<UserModel> findPageUserInfoByCondition(Map<String, Object> conditionMap, Pageable pageable) {
		return userDao.findPageUserInfoByCondition(conditionMap,pageable);
	}

	@Override
	public UserModel findUserById(Long id) {
		return userDao.findUserById(id);
	}

	@Override
	public void saveUser(UserModel sysUser) {
		userDao.saveUser(sysUser);
	}

	@Override
	public List findAllUsers() {
		return userDao.findAllUsers();
	}

}
