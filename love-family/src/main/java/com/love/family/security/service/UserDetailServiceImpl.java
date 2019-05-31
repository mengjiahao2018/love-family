package com.love.family.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.love.family.business.loginManage.entity.SysUser;
import com.love.family.business.loginManage.service.LoginService;
import com.love.family.security.model.UserInfoImpl;
import com.love.family.utils.MyBusinessException;

public class UserDetailServiceImpl implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	
	@Autowired
	private LoginService loginService;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			SysUser sysUser = findUser(username);
			return new UserInfoImpl(sysUser);  
		} catch (Exception e) {
			logger.error("获取用户\"{}\"的信息时错误!", username);
			e.printStackTrace();
			throw new MyBusinessException(e.getMessage());
		}
	}

	private SysUser findUser(String username) {
		List users = loginService.findLoginUserByLoginName(username);
		SysUser sysUser;
		if(users==null||users.size()==0) {
			return null;
		}else if(users.size()>1) {
			throw new UsernameNotFoundException("系统错误：根据用户名["+username+"]找到"+users.size()+"个用户");
		}else {
			sysUser = (SysUser) users.get(0);
		}
		return sysUser;
	}

}
