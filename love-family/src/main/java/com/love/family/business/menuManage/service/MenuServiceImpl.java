package com.love.family.business.menuManage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.menuManage.entity.MenuVO;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuBiz menuBiz;
	
	Map<String,String> allMenuCodeAndUrl;//所有的菜单
	
	List<MenuVO> childMenuList;//叶子节点
	
	Map<Long,MenuVO> parentMenuList;//所有的父节点
	
	@Override
	public List<MenuVO> findDemoMenuForUser(HttpServletRequest request) {
		//从数据库中
		allMenuCodeAndUrl = menuBiz.findAllMenuCodeAndUrl();
		childMenuList = menuBiz.findAllChildNodeForUser(allMenuCodeAndUrl.keySet());
		parentMenuList = menuBiz.findAllParentNodeForUser(allMenuCodeAndUrl.keySet());
		
		Map<Long,MenuVO> demoMenuForUser = new HashMap<Long, MenuVO>();
		List<MenuVO> treeDemoMenu = new ArrayList<MenuVO>();
		menuBiz.findNodeForUser(childMenuList,parentMenuList,demoMenuForUser,treeDemoMenu);
		//去掉非该角色菜单
		filterTreeMenu(treeDemoMenu,allMenuCodeAndUrl.keySet());
		
		MenuVO mainPageMenuEO = new MenuVO();
		mainPageMenuEO.setCode("0SHOUYE");
		mainPageMenuEO.setIcon("fa-home");
		mainPageMenuEO.setLabel("首页");
		treeDemoMenu.add(mainPageMenuEO);
		
		menuBiz.sortTreeDemoMenu(treeDemoMenu);
		menuBiz.replaceCodeByUrl(allMenuCodeAndUrl,treeDemoMenu);
		return treeDemoMenu;
	}

	private void filterTreeMenu(List<MenuVO> treeDemoMenu, Set<String> codes) {
		for (Iterator<MenuVO> iter = treeDemoMenu.iterator(); iter.hasNext();) {
			MenuVO menu = iter.next();
			String pCode = menu.getCode();
			if(!codes.contains(pCode)) {
				iter.remove();
			}
			List<MenuVO> lMenuEO = menu.getDemoMenus();
			if(lMenuEO!=null)
				filterTreeMenu(lMenuEO, codes);
		}
	}

	@Override
	public MenuEO findMenuByCode(String code) {
		return menuBiz.findMenuByCode(code);
	}

	@Override
	public List<Object[]> findMenuByLabel(String label) {
		return menuBiz.findMenuByLabel(label);
	}

	@Override
	public Page<MenuEO> queryPageMenu(Pageable pageable, MenuEO menuEO) {
		return menuBiz.queryPageMenu(pageable,menuEO);
	}

}
