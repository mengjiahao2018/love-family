package com.love.family.userManage.dao;

import java.util.List;
import java.util.Map;

import com.love.family.userManage.entity.UserInfo;

public interface UserManageDao {

	public List<UserInfo> findUserInfoList(Map<String, Object> param) throws Exception;
}
