package com.love.family.business.functionManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.love.family.business.functionManage.dao.FunctionDao;
import com.love.family.business.functionManage.dao.RoleFunctionDao;
import com.love.family.business.functionManage.entity.GenericFunction;
import com.love.family.business.functionManage.entity.RoleFunction;
import com.love.family.utils.MyBusinessException;

@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionDao functionDao;
	
	@Autowired
	protected RoleFunctionDao roleFunctionDao;

	@Override
	public List<GenericFunction> findFunctionsByRoleId(Long roleId) {
		return functionDao.findFunctionsByRoleId(roleId);
	}

	@Override
	public List<GenericFunction> getAllFunctions() {
		return functionDao.getAllFunctions();
	}

	@Override
	public Page<GenericFunction> findFunctionByNameOrUrl(Map<String, Object> conditionMap,Pageable pageable) {
		return functionDao.findFunctionByNameOrUrl(conditionMap,pageable);
	}

	@Override
	public List<GenericFunction> findMenuFuncionByCode(String code) {
		return functionDao.findMenuFuncionByCode(code);
	}

	@Override
	public void save(GenericFunction function) {
		functionDao.save(function);
	}

	@Override
	public GenericFunction findFunctionByFunctionId(Long id) {
		return functionDao.findFunctionByFunctionId(id);
	}

	@Override
	public void deleteFuncion(GenericFunction function) {
		if(function.getId()==null) {
			throw new MyBusinessException("删除功能必须指定已有的功能ID！");
		}
		deleteAllAffiliated(function);
		functionDao.deleteFuncion(function);
	}

	public void deleteAllAffiliated(GenericFunction function) {
		List<RoleFunction> rfs = roleFunctionDao.findByFunctionId(function.getId());
		roleFunctionDao.deleteAll(rfs);
	}

}
