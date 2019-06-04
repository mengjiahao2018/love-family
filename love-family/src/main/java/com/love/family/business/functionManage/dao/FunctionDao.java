package com.love.family.business.functionManage.dao;

import java.util.List;

import com.love.family.business.functionManage.entity.GenericFunction;

public interface FunctionDao {

	List<GenericFunction> findFunctionsByRoleId(Long roleId);

	List<GenericFunction> getAllFunctions();

}
