package com.love.family.business.functionManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<GenericFunction> findFunctionByNameOrUrl(Map<String, Object> conditionMap,Pageable pageable) {
		StringBuffer buffer = new StringBuffer();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		buffer.append("from GenericFunction f where 1=1 ");
		if(StringUtils.isNotBlank((String)conditionMap.get("functionName"))) {
			buffer.append(" and f.name like '%"+(String)conditionMap.get("functionName")+"%'");
		}
		if(StringUtils.isNotBlank((String)conditionMap.get("functionUrl"))) {
			buffer.append(" and f.url like '%"+(String)conditionMap.get("functionUrl")+"%'");
		}
		return this.findEntityObjects(buffer.toString(), paramMap, pageable);
	}

	@Override
	public List<GenericFunction> findMenuFuncionByCode(String code) {
		StringBuffer buffer = new StringBuffer();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		buffer.append("from GenericFunction f where 1=1 and f.code = :code");
		paramMap.put("code", code);
		List<GenericFunction> functions = this.findEntityObjects(buffer.toString(), paramMap);
		return functions;
	}

	@Override
	public GenericFunction findFunctionByFunctionId(Long id) {
		return this.load(id);
	}

	@Override
	public void deleteFuncion(GenericFunction function) {
		this.delete(function);
	}

}
