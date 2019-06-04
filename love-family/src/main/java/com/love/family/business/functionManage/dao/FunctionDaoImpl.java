package com.love.family.business.functionManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.love.family.business.functionManage.entity.GenericFunction;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class FunctionDaoImpl extends BaseHibernate4QueryDao<GenericFunction> implements FunctionDao {

	@Override
	public List<GenericFunction> findFunctionsByRoleId(Long roleId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from GenericFunction f where f.id in  ");
		buffer.append(" (select fr.functionId from RoleFunction fr where fr.roleId=:roleId)");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleId", roleId);
		List<GenericFunction> functionList = this.findEntityObjects(buffer.toString(), conditionMap);
		return functionList;

	}

	@Override
	public List<GenericFunction> getAllFunctions() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from GenericFunction f");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		List<GenericFunction> functionList = this.findEntityObjects(buffer.toString(), conditionMap);
		return functionList;
	}

}
