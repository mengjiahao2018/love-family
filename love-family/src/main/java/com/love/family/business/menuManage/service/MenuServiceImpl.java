package com.love.family.business.menuManage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love.family.business.menuManage.entity.MenuEO;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuBiz menuBiz;
	
	Map<String,String> allMenuCodeAndUrl;//所有的菜单
	
	List<MenuEO> childMenuList;//叶子节点
	
	Map<Long,MenuEO> parentMenuList;//所有的父节点
	
	@Override
	public List<MenuEO> findDemoMenuForUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
