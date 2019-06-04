package com.love.family.business.roleManage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.business.functionManage.entity.GenericFunction;
import com.love.family.business.functionManage.service.FunctionService;
import com.love.family.business.loginManage.entity.GenericRole;
import com.love.family.business.roleManage.dao.RoleDao;
import com.love.family.business.roleManage.dao.UserRoleDao;
import com.love.family.pub.rbac.privilege.model.RolePrivilege;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private FunctionService functionService;

	@Override
	public List findRolesByUserId(Long id) {
		return userRoleDao.findRolesByUserId(id);
	}

	@Override
	public GenericRole findRoleById(Long roleId) {
		return roleDao.findRoleById(roleId);
	}

	@Override
	public RolePrivilege getRolePrivilege(Long roleId) {
		RolePrivilege privilege = null;
		privilege = loadRolePrivilege(roleId);
		return privilege;
	}

	protected RolePrivilege loadRolePrivilege(Long roleId) {
		RolePrivilege rolePrivilege = new RolePrivilege(roleId);
		List<GenericFunction> functionList = functionService.findFunctionsByRoleId(roleId);
		List<String> resourceList = new ArrayList<String>();
		Map<String,String> menuMap = new HashMap<String, String>();
		for(GenericFunction function:functionList) {
			resourceList.add(function.getUrl());
			if(GenericFunction.MENU_TYPE.equals(function.getType())) {
				menuMap.put(function.getCode(),function.getUrl());
			}
		}
		rolePrivilege.setMenuMap(menuMap);
		rolePrivilege.setResourceList(resourceList);
		return rolePrivilege;
	}

}
