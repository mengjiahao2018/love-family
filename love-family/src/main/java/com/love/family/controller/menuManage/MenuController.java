package com.love.family.controller.menuManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.love.family.business.functionManage.entity.GenericFunction;
import com.love.family.business.functionManage.service.FunctionService;
import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.menuManage.entity.MenuVO;
import com.love.family.business.menuManage.service.MenuService;
import com.love.family.controller.roleManage.RoleSelectController;
import com.love.family.pub.rbac.system.entity.User;
import com.love.family.pub.rbac.system.util.UserInfoUtils;
import com.love.family.utils.MessageUtil;

@Controller
@RequestMapping("/menuManage")
public class MenuController {
	Logger logger = LoggerFactory.getLogger(RoleSelectController.class);

	@Autowired
	MenuService menuService;
	
	@Autowired
	FunctionService functionService;

	@RequestMapping(value = "/queryFirstMenus", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object[] queryMenus(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Object result[] = new Object[2];
		List<MenuVO> menus = menuService.findDemoMenuForUser(request);
		User u = (User) UserInfoUtils.getCurrentUserInfo().getSimpleUser();
		result[0] = u;
		result[1] = menus;
		return result;
	}
	
	@RequestMapping(value = "/queryAllMenu", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryAllMenu(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		int page = Integer.parseInt((String)pageRequest.get("pageRequest[page]"));
		int size = Integer.parseInt((String)pageRequest.get("pageRequest[size]"));
		Sort sort = new Sort(Sort.Direction.DESC,(String)pageRequest.get("pageRequest[sort]"));
		
		String menuName = (String)pageRequest.get("menuName");
		Pageable pageable = PageRequest.of(page-1, size, sort);

		MenuEO menuEO = new MenuEO();
		menuEO.setLabel("%"+(StringUtils.isNotBlank(menuName)?menuName:"")+"%");
		
		try {
			Page<MenuEO> data = menuService.queryPageMenu(pageable,menuEO);
			List<MenuEO> demoMenuEOs = data.getContent();
			List<MenuVO> menuVOs = new ArrayList<MenuVO>();
			
			for(MenuEO demoMenuEO : demoMenuEOs) {
				MenuVO menuVO = new MenuVO();
				BeanUtils.copyProperties(demoMenuEO, menuVO);
				if(demoMenuEO.getCode()!=null) {
					List<GenericFunction> functions = functionService.findMenuFuncionByCode(demoMenuEO.getCode());
					if(functions!=null&&functions.size()!=1) {
						resultMap.put("error", "function配置有误，code对应的function不存在或者存在多条数据！错误code"+demoMenuEO.getCode());
					}else if(functions!=null&&functions.size()==1) {
						menuVO.setFunctionName(functions.get(0).getName());
						menuVO.setFunctionUrl(functions.get(0).getUrl());
					}
				}
				menuVOs.add(menuVO);
			}
			resultMap.put("totalCount", data.getTotalElements());
			resultMap.put("resultList", menuVOs);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}

}
