package com.love.family.business.functionManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.functionManage.entity.FunctionModel;

public interface FunctionService {
	
	/**
	 * 根据roleId查询改角色下的所有功能
	 * @param roleId
	 * @return
	 */
	List<FunctionModel> findFunctionsByRoleId(Long roleId);

	/**
	 * 得到所有的配置功能信息，以便在访问功能时，知道那个页面或者功能可以访问
	 * @return
	 */
	List<FunctionModel> getAllFunctions();

	/**
	 * 通过功能名称和功能链接模糊查询功能信息
	 * @param conditionMap
	 * @return
	 */
	Page<FunctionModel> findFunctionByNameOrUrl(Map<String, Object> conditionMap,Pageable pageable);
	/**
	 * 
	 * @param code
	 * @return
	 */
	List<FunctionModel> findMenuFuncionByCode(String code);
	/**
	 * 新增或更新功能
	 * @param function
	 */
	void save(FunctionModel function);
	/**
	 * 根据功能ID查询功能
	 * @param id
	 * @return
	 */
	FunctionModel findFunctionByFunctionId(Long id);
	
	/**
	 * 删除功能菜单
	 * @param function
	 */
	void deleteFuncion(FunctionModel function);
	/**
	 * 根据菜单名称查询菜单
	 * @param param
	 * @return
	 */
	List<FunctionModel> findFunctionByLikeName(String param);

	List<FunctionModel> findAllFunction();

}
