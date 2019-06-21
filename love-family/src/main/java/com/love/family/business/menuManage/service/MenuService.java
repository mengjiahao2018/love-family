package com.love.family.business.menuManage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.menuManage.entity.MenuVO;

public interface MenuService {

	List<MenuVO> findDemoMenuForUser(HttpServletRequest request);

	MenuEO findMenuByCode(String code);

	List<Object[]> findMenuByLabel(String param);
	/**
	 * 分页查询条件
	 * @param pageable
	 * @param menuEO
	 * @return
	 */
	Page<MenuEO> queryPageMenu(Pageable pageable, MenuEO menuEO);

}
