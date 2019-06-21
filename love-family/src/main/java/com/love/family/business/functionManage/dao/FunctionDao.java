package com.love.family.business.functionManage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.functionManage.entity.GenericFunction;

public interface FunctionDao {

	List<GenericFunction> findFunctionsByRoleId(Long roleId);

	List<GenericFunction> getAllFunctions();

	Page<GenericFunction> findFunctionByNameOrUrl(Map<String, Object> conditionMap,Pageable pageable);

	List<GenericFunction> findMenuFuncionByCode(String code);

	void save(GenericFunction function);

	GenericFunction findFunctionByFunctionId(Long id);

	void deleteFuncion(GenericFunction function);

	List<GenericFunction> findFunctionByLikeName(String param);

}
