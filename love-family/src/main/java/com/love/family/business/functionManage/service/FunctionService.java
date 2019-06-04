package com.love.family.business.functionManage.service;

import java.util.List;

import com.love.family.business.functionManage.entity.GenericFunction;

public interface FunctionService {
	
	/**
	 * 根据roleId查询改角色下的所有功能
	 * @param roleId
	 * @return
	 */
	List<GenericFunction> findFunctionsByRoleId(Long roleId);

	/**
	 * 得到所有的配置功能信息，以便在访问功能时，知道那个页面或者功能可以访问
	 * @return
	 */
	List<GenericFunction> getAllFunctions();

}
