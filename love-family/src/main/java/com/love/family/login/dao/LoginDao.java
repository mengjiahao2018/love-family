package com.love.family.login.dao;

import com.love.family.login.entity.LoginUser;

public interface LoginDao {
	boolean login(String loginName, String password);

	boolean addLoginUser(String loginName, String password);

	LoginUser findLoginUserByLoginName(String loginName);
}
