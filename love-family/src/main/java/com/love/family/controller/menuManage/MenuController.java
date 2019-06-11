package com.love.family.controller.menuManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.menuManage.service.MenuService;
import com.love.family.pub.rbac.system.entity.User;
import com.love.family.pub.rbac.system.util.UserInfoUtils;

@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	MenuService menuService;

	@RequestMapping(value = "/queryFirstMenus", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object[] queryMenus(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Object result[] = new Object[2];
		List<MenuEO> menus = menuService.findDemoMenuForUser(request);
		User u = (User) UserInfoUtils.getCurrentUserInfo().getSimpleUser();
		result[0] = u;
		result[1] = menus;
		return result;
	}

}
