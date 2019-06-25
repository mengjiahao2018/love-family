package com.love.family.business.loginManage.service;

import java.util.List;
import java.util.Map;

import com.love.family.business.userManage.entity.UserModel;


public interface LoginService {
	boolean login(Map<String,Object> param);

	boolean addLoginUser(Map<String, Object> pageRequest);

	List<UserModel> findLoginUserByLoginName(String loginName);
}
