package com.love.family.loginManage.dao;

import com.love.family.loginManage.entity.LoginUser;

public interface LoginDao {
	boolean login(String loginName, String password);

	boolean addLoginUser(String loginName, String password);

	LoginUser findLoginUserByLoginName(String loginName);
}
