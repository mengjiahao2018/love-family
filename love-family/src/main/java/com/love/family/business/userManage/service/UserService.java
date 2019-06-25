package com.love.family.business.userManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.userManage.entity.SysUser;

public interface UserService {
	/**
	 * 用户查询
	 * @param conditionMap
	 * @param pageable
	 * @return
	 */
	Page<SysUser> findPageUserInfoByCondition(Map<String, Object> conditionMap, Pageable pageable);

	SysUser findUserById(Long id);

	void saveUser(SysUser sysUser);

	List findAllUsers();

}
