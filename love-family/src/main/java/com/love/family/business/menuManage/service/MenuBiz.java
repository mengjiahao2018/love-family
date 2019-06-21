package com.love.family.business.menuManage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.love.family.business.menuManage.dao.MenuDao;
import com.love.family.business.menuManage.dao.MenuRepo;
import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.menuManage.entity.MenuVO;
import com.love.family.security.context.SecurityContext;

@Component
public class MenuBiz {
	
	@Autowired
	MenuRepo menuRepo;
	
	@Autowired
	MenuDao menuDao;

	/**
	 * 返回用对应可用的菜单Map<key:code,value:url>
	 * 
	 * @return
	 */
	public Map<String, String> findAllMenuCodeAndUrl() {
		return SecurityContext.getCurrentUser().getMenus();
	}

	/**
	 * 获取所有叶子菜单
	 * 
	 * @param keySet
	 * @return
	 */
	public List<MenuVO> findAllChildNodeForUser(Set<String> codes) {
		List<MenuVO> demoMenuList = new ArrayList<MenuVO>();
		List<MenuVO> demoMenuEOss;
		demoMenuEOss = menuRepo.findAllBottomMenu();
		for(MenuVO demoMenuEO : demoMenuEOss) {
			if(StringUtils.isBlank(demoMenuEO.getCode())) {
				throw new RuntimeException(demoMenuEO.getId()+"作为最底层的子节点code字段不得为空！");
			}else {
				demoMenuList.add(demoMenuEO);
			}
		}
		return demoMenuList;
	}

	/**
	 * 获取父菜单
	 * 
	 * @param keySet
	 * @return
	 */
	public Map<Long, MenuVO> findAllParentNodeForUser(Set<String> keySet) {
		Map<Long,MenuVO> demoMenuList = new HashMap<Long, MenuVO>();
		List<MenuVO> demoMenuEOss;
		demoMenuEOss = menuRepo.findAllParentMenu();
		for(MenuVO demoMenuEO : demoMenuEOss) {
			//只要子菜单加入角色，父节点自动能展示
			if(!StringUtils.isBlank(demoMenuEO.getCode())) {
				demoMenuList.put(demoMenuEO.getId(), demoMenuEO);
			}
		}
		return demoMenuList;
	}

	/**
	 * 将各级别菜单组成最终菜单List列表
	 * 
	 * @param childMenuList
	 * @param parentMenuList
	 * @param demoMenuForUser
	 * @param treeDemoMenu
	 */
	public void findNodeForUser(List<MenuVO> childMenuList, Map<Long, MenuVO> parentMenuList,
			Map<Long, MenuVO> demoMenuForUser, List<MenuVO> treeDemoMenu) {
		for(MenuVO demoMenuEO:childMenuList) {
			if(demoMenuEO.getParentId()==null) {
				treeDemoMenu.add(demoMenuEO);
				demoMenuForUser.put(demoMenuEO.getId(), demoMenuEO);
			}else {
				if(demoMenuForUser.containsKey(demoMenuEO.getParentId())) {
					demoMenuForUser.get(demoMenuEO.getParentId()).getDemoMenus().add(demoMenuEO);
				}else {
					MenuVO demoMenuEO1 = parentMenuList.get(demoMenuEO.getParentId());
					if(demoMenuEO1!=null) {
						ArrayList<MenuVO> demoMenu1 = new ArrayList<MenuVO>();
						demoMenu1.add(demoMenuEO);
						demoMenuEO1.setDemoMenus(demoMenu1);
						demoMenuForUser.put(demoMenuEO1.getId(), demoMenuEO1);
						
						ArrayList<MenuVO> demoMeneEos = new ArrayList<MenuVO>();
						demoMeneEos.add(demoMenuEO1);
						findNodeForUser(demoMeneEos, parentMenuList, demoMenuForUser, treeDemoMenu);
					}
				}
			}
		}
	}

	public void sortTreeDemoMenu(List<MenuVO> treeDemoMenu) {
		Collections.sort(treeDemoMenu);
		for(MenuVO menuEO : treeDemoMenu) {
			if(menuEO.getDemoMenus()!=null) {
				this.sortTreeDemoMenu(menuEO.getDemoMenus());
			}
		}
	}

	public void replaceCodeByUrl(Map<String, String> codeUrlMap, List<MenuVO> sourceList) {
		for(MenuVO demoMenuEO : sourceList) {
			if("0SHOUYE".equals(demoMenuEO.getCode())) {
				demoMenuEO.setCode("/templates/mainPage/mainPage.html");
			}else {
				demoMenuEO.setCode(codeUrlMap.get(demoMenuEO.getCode()));
				if(demoMenuEO.getDemoMenus()!=null&&demoMenuEO.getDemoMenus().size()!=0) {
					replaceCodeByUrl(codeUrlMap, demoMenuEO.getDemoMenus());
				}
			}
		}
	}

	public MenuEO findMenuByCode(String code) {
		return menuDao.findMenuByCode(code);
	}

	public List<Object[]> findMenuByLabel(String label) {
		return menuRepo.findMenuByLabel(label);
	}

	public Page<MenuEO> queryPageMenu(Pageable pageable, MenuEO menuEO) {
		return menuDao.queryPageMenu(pageable,menuEO);
	}

}
