package com.love.family.business.loginManage.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.business.loginManage.dao.LoginDao;
import com.love.family.business.userManage.entity.UserModel;
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
		List<UserModel> sysUserList = userDao.findLoginUserByLoginName(loginName);
		if(sysUserList.size()>0){
			throw new MyBusinessException("用户名已经存在，不可重新注册!");
		}
		
		return userDao.addLoginUser(loginName,password);
	}

	@Override
	public List<UserModel> findLoginUserByLoginName(String loginName) {
		return userDao.findLoginUserByLoginName(loginName);
	}

}
