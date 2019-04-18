package com.love.family.business.userManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.business.userManage.dao.UserManageDao;
import com.love.family.business.userManage.entity.UserInfo;

@Service
public class UserManageServiceImpl implements UserManageService {

	@Autowired
	UserManageDao userManageDao;

	@Override
	public List<UserInfo> findUserInfoList(Map<String, Object> param) throws Exception {
		return userManageDao.findUserInfoList(param);
	}

}
