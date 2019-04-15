package com.love.family.login.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.login.dao.LoginDao;
import com.love.family.login.entity.LoginUser;
import com.love.family.utils.MyBusinessException;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao userDao;
	
	@Override
	public boolean login(Map<String,Object> param) {
		String loginName = (String)param.get("loginName");
		String password =(String)param.get("password");
		if(StringUtils.isBlank(loginName)){
			throw new MyBusinessException("用户名不可以为空!");
		}
		if(StringUtils.isBlank(password)){
			throw new MyBusinessException("密码不可以为空!");
		}
		return userDao.login(loginName,password);
	}

	@Override
	public boolean addLoginUser(Map<String, Object> param) {
		
		String loginName = (String)param.get("loginName");
		String password =(String)param.get("password");
		if(StringUtils.isBlank(loginName)){
			throw new MyBusinessException("用户名不可以为空!");
		}
		if(StringUtils.isBlank(password)){
			throw new MyBusinessException("密码不可以为空!");
		}
		
		//新增用户前查看用户是否已经存在
		LoginUser loginUser = userDao.findLoginUserByLoginName(loginName);
		if(loginUser!=null){
			throw new MyBusinessException("用户名已经存在，不可重新注册!");
		}
		
		return userDao.addLoginUser(loginName,password);
	}

}
