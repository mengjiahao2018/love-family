package com.love.family.business.menuManage.dao;

import com.love.family.business.menuManage.entity.MenuEO;

public interface MenuDao {

	MenuEO findMenuByCode(String code);

}
