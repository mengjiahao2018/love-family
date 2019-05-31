package com.love.family.business.roleManage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.business.roleManage.dao.UserRoleDao;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public List findRolesByUserId(Long id) {
		return userRoleDao.findRolesByUserId(id);
	}

}
