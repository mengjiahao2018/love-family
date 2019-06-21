package com.love.family.business.menuManage.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.menuManage.entity.MenuEO;

public interface MenuDao {

	MenuEO findMenuByCode(String code);

	Page<MenuEO> queryPageMenu(Pageable pageable, MenuEO menuEO);

}
