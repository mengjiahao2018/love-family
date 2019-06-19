package com.love.family.business.menuManage.dao;

import java.util.List;

import com.love.family.business.menuManage.entity.MenuVO;

public interface MenuRepo {
	/**
	 * 获取菜单树中最底层的节点
	 * @return
	 */
	List<MenuVO> findAllBottomMenu();
	/**
	 * 获取菜单树中叶子节点
	 * @return
	 */
	List<MenuVO> findAllParentMenu();

}
