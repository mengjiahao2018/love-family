package com.love.family.controller.roleManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.love.family.business.loginManage.entity.SysRole;
import com.love.family.business.roleManage.service.RoleService;
import com.love.family.pub.rbac.system.entity.User;
import com.love.family.pub.rbac.system.util.UserInfoUtils;
import com.love.family.security.model.UserInfo;

@Controller
public class RoleSelectController {
	Logger logger = LoggerFactory.getLogger(RoleSelectController.class);

	@Autowired
	public RoleService roleService;

	@RequestMapping(value = "getRoles", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectRole(HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo =(UserInfo)UserInfoUtils.getCurrentUserInfo();
		if (userInfo == null) {
			return null;
		} else {
			@SuppressWarnings("rawtypes")
			List roles = roleService.findRolesByUserId(userInfo.getId());
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("roleList", roles);
			return resultMap;
		}

	}
	
	@RequestMapping(value = "selectOneRole")
	public ModelAndView selectOneRole(HttpServletRequest request, HttpServletResponse response) {
		try {
			UserInfo userInfo =(UserInfo)UserInfoUtils.getCurrentUserInfo();
			User selfUser = (User)userInfo.getSimpleUser();
			String selectedRoleId = request.getParameter("selectedRoleId");
			Long roleId = StringUtils.isBlank(selectedRoleId)?0:Long.valueOf(selectedRoleId);
			SysRole role = roleService.findRoleById(roleId) ;
			selfUser.setCurrentSelectRole(role);
			initUser(userInfo,Long.valueOf(selectedRoleId));
		} catch (NumberFormatException e) {
			ModelAndView indexView = new ModelAndView("index");
			return indexView;
		}
		ModelAndView indexView = new ModelAndView("index");
		return indexView;
	}

	private void initUser(UserInfo userInfo, Long valueOf) {
		
	}
}
