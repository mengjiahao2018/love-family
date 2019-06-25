package com.love.family.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.love.family.business.loginManage.service.LoginService;
import com.love.family.business.userManage.entity.UserModel;
import com.love.family.pub.rbac.system.entity.User;
import com.love.family.security.model.UserInfoImpl;
import com.love.family.utils.MyBusinessException;

public class UserDetailServiceImpl implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	
	@Autowired
	private LoginService loginService;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			UserModel sysUser = findUser(username);
			User user = new User();
			BeanUtils.copyProperties(sysUser, user);
			return new UserInfoImpl(user);  
		} catch (Exception e) {
			logger.error("获取用户\"{}\"的信息时错误!", username);
			e.printStackTrace();
			throw new MyBusinessException(e.getMessage());
		}
	}

	private UserModel findUser(String username) {
		List users = loginService.findLoginUserByLoginName(username);
		UserModel sysUser;
		if(users==null||users.size()==0) {
			return null;
		}else if(users.size()>1) {
			throw new UsernameNotFoundException("系统错误：根据用户名["+username+"]找到"+users.size()+"个用户");
		}else {
			sysUser = (UserModel) users.get(0);
		}
		return sysUser;
	}

}
