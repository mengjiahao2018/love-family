package com.love.family.controller.roleManage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.love.family.business.roleManage.entity.GenericRole;
import com.love.family.business.roleManage.service.RoleService;
import com.love.family.utils.MessageUtil;

@Controller
@RequestMapping("/roleManage")
public class RoleController {
	Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	public RoleService roleService;

	@RequestMapping(value = "/queryAllRole", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryAllRole(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		int page = Integer.parseInt((String)pageRequest.get("pageRequest[page]"));
		int size = Integer.parseInt((String)pageRequest.get("pageRequest[size]"));
		Sort sort = new Sort(Sort.Direction.DESC,(String)pageRequest.get("pageRequest[sort]"));
		
		String roleName = (String)pageRequest.get("roleName");
		Pageable pageable = PageRequest.of(page-1, size, sort);

		GenericRole role = new GenericRole();
		role.setName("%"+(StringUtils.isNotBlank(roleName)?roleName:"")+"%");
		
		try {
			Page<GenericRole> data = roleService.queryPageRole(pageable,role);			
			resultMap.put("totalCount", data.getTotalElements());
			resultMap.put("resultList", data.getContent());
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}
}
