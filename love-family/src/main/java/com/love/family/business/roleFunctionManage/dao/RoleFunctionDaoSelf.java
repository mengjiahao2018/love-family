package com.love.family.business.roleFunctionManage.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.roleFunctionManage.entity.RoleFunction;

public interface RoleFunctionDaoSelf {

	List<RoleFunction> findByFunctionId(Long id);

	void deleteByRoleFunctionId(Long id);

	Page<RoleFunction> queryRoleFunctionByRoleNameAndFunctionName(Pageable pageable, String roleName,
			String functionName);

	void deleteRoleFunction(RoleFunction roleFunction);

	void saveRoleFunction(RoleFunction roleFunction);

	List findRoleFunctionByRoleIdAndFunction(Long roleId, Long functionId);

}
