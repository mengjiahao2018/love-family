package com.love.family.business.functionManage.dao;

import java.util.List;

import com.love.family.business.functionManage.entity.RoleFunction;

public interface RoleFunctionDaoSelf {

	List<RoleFunction> findByFunctionId(Long id);

	void deleteByRoleFunctionId(Long id);

}
