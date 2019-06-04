package com.love.family.business.functionManage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.business.functionManage.dao.FunctionDao;
import com.love.family.business.functionManage.entity.GenericFunction;

@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	FunctionDao functionDao;

	@Override
	public List<GenericFunction> findFunctionsByRoleId(Long roleId) {
		return functionDao.findFunctionsByRoleId(roleId);
	}

	@Override
	public List<GenericFunction> getAllFunctions() {
		return functionDao.getAllFunctions();
	}

}
