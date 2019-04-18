package com.love.family.business.userManage.dao;

import java.util.List;
import java.util.Map;

import com.love.family.business.userManage.entity.UserInfo;

public interface UserManageDao {

	public List<UserInfo> findUserInfoList(Map<String, Object> param) throws Exception;
}
