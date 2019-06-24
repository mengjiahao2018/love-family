package com.love.family.business.roleFunctionManage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.love.family.business.roleFunctionManage.dao.RoleFunctionDaoSelf;
import com.love.family.business.roleFunctionManage.entity.RoleFunction;

@Service
public class RoleFunctionServiceImpl implements RoleFunctionService {

	@Autowired
	RoleFunctionDaoSelf roleFunctionDaoSelf;

	@Override
	public Page<RoleFunction> queryRoleFunctionByRoleNameAndFunctionName(Pageable pageable, String roleName,
			String functionName) {
		return roleFunctionDaoSelf.queryRoleFunctionByRoleNameAndFunctionName(pageable, roleName, functionName);
	}

	@Override
	public void deleteRoleFunction(RoleFunction roleFunction) {
		roleFunctionDaoSelf.deleteRoleFunction(roleFunction);
	}

	@Override
	public void saveRoleFunction(RoleFunction roleFunction) {
		roleFunctionDaoSelf.saveRoleFunction(roleFunction);
	}

	@Override
	public List findRoleFunctionByRoleIdAndFunction(Long roleId, Long functionId) {
		return roleFunctionDaoSelf.findRoleFunctionByRoleIdAndFunction(roleId,functionId);
	}

}
