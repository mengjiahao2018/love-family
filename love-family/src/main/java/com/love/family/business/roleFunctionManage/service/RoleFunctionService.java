package com.love.family.business.roleFunctionManage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.roleFunctionManage.entity.RoleFunction;

public interface RoleFunctionService {

	/**
	 * 根据角色名字和功能名字查询角色菜单信息
	 * @param pageable
	 * @param roleName
	 * @param functionName
	 * @return
	 */
	Page<RoleFunction> queryRoleFunctionByRoleNameAndFunctionName(Pageable pageable, String roleName,
			String functionName);

	void deleteRoleFunction(RoleFunction roleFunction);

	void saveRoleFunction(RoleFunction roleFunction);

	List findRoleFunctionByRoleIdAndFunction(Long roleId, Long functionId);

}
