package com.love.family.business.menuManage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.menuManage.entity.MenuVO;

public interface MenuService {

	List<MenuVO> findDemoMenuForUser(HttpServletRequest request);

	MenuEO findMenuByCode(String code);

}
