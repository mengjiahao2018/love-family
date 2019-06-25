package com.love.family.business.functionManage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.functionManage.entity.FunctionModel;

public interface FunctionDao {

	List<FunctionModel> findFunctionsByRoleId(Long roleId);

	List<FunctionModel> getAllFunctions();

	Page<FunctionModel> findFunctionByNameOrUrl(Map<String, Object> conditionMap,Pageable pageable);

	List<FunctionModel> findMenuFuncionByCode(String code);

	void save(FunctionModel function);

	FunctionModel findFunctionByFunctionId(Long id);

	void deleteFuncion(FunctionModel function);

	List<FunctionModel> findFunctionByLikeName(String param);

}
