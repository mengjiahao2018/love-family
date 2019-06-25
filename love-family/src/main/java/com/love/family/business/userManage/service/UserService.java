package com.love.family.business.userManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.userManage.entity.UserModel;

public interface UserService {
	/**
	 * 用户查询
	 * @param conditionMap
	 * @param pageable
	 * @return
	 */
	Page<UserModel> findPageUserInfoByCondition(Map<String, Object> conditionMap, Pageable pageable);

	UserModel findUserById(Long id);

	void saveUser(UserModel sysUser);

	List findAllUsers();

}
