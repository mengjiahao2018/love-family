package com.love.family.business.userManage.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.userManage.entity.SysUser;

public interface UserDao {

	Page<SysUser> findPageUserInfoByCondition(Map<String, Object> conditionMap, Pageable pageable);

	SysUser findUserById(Long id);

	void saveUser(SysUser sysUser);

}
