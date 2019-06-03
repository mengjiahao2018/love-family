package com.love.family.business.roleManage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.business.loginManage.entity.SysRole;
import com.love.family.business.roleManage.dao.RoleDao;
import com.love.family.business.roleManage.dao.UserRoleDao;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public List findRolesByUserId(Long id) {
		return userRoleDao.findRolesByUserId(id);
	}

	@Override
	public SysRole findRoleById(Long roleId) {
		return roleDao.findRoleById(roleId);
	}

}
