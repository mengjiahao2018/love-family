package com.love.family.business.menuManage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.love.family.business.menuManage.entity.MenuEO;

public interface MenuService {

	List<MenuEO> findDemoMenuForUser(HttpServletRequest request);

}
