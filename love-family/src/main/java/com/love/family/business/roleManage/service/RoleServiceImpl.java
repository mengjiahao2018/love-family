package com.love.family.business.roleManage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.love.family.business.functionManage.entity.FunctionModel;
import com.love.family.business.functionManage.service.FunctionService;
import com.love.family.business.roleManage.dao.RoleDao;
import com.love.family.business.roleManage.entity.RoleInfo;
import com.love.family.security.model.RolePrivilege;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private FunctionService functionService;

	@Override
	public List findRolesByUserId(Long id) {
		return roleDao.findRolesByUserId(id);
	}

	@Override
	public RoleInfo findRoleById(Long roleId) {
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
		List<FunctionModel> functionList = functionService.findFunctionsByRoleId(roleId);
		List<String> resourceList = new ArrayList<String>();
		Map<String,String> menuMap = new HashMap<String, String>();
		for(FunctionModel function:functionList) {
			resourceList.add(function.getUrl());
			if(FunctionModel.MENU_TYPE.equals(function.getType())) {
				menuMap.put(function.getCode(),function.getUrl());
			}
		}
		rolePrivilege.setMenuMap(menuMap);
		rolePrivilege.setResourceList(resourceList);
		return rolePrivilege;
	}

	@Override
	public Page<RoleInfo> queryPageRole(Pageable pageable, RoleInfo role) {
		return roleDao.queryPageRole(pageable,role);
	}

	@Override
	public List<RoleInfo> searchRoleDataByCodeUpd(Long id, String roleCode) {
		return roleDao.searchRoleDataByCodeUpd(id,roleCode);
	}

	@Override
	public List<RoleInfo> searchRoleDataByCode(String roleCode) {
		return roleDao.searchRoleDataByCode(roleCode);
	}

	@Override
	public void saveRole(RoleInfo role) {
		roleDao.saveRole(role);
	}

	@Override
	public RoleInfo findRoleByRoleId(Long id) {
		return roleDao.findRoleById(id);
	}

	@Override
	public void deleteRolerole(RoleInfo role) {
		roleDao.deleteRolerole(role);
	}

	@Override
	public List<RoleInfo> findAllRole() {
		return roleDao.findAllRole();
	}

}
