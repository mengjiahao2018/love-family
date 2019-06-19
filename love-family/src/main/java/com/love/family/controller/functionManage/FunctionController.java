package com.love.family.controller.functionManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.love.family.business.menuManage.service.MenuService;
import com.love.family.utils.MessageUtil;
import com.love.family.utils.MyBusinessException;

@Controller
@RequestMapping("/function")
public class FunctionController {

	@Autowired
	FunctionService functionService;
	
	@Autowired
	MenuService menuService;
	

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryMenus(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		int page = Integer.parseInt((String)pageRequest.get("pageRequest[page]"));
		int size = Integer.parseInt((String)pageRequest.get("pageRequest[size]"));
		Sort sort = new Sort(Sort.Direction.DESC,(String)pageRequest.get("pageRequest[sort]"));
		
		String functionName = (String)pageRequest.get("functionName");
		String functionUrl = (String)pageRequest.get("functionUrl");
		
		Pageable pageable = PageRequest.of(page-1, size, sort);
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("functionName", functionName);
		conditionMap.put("functionUrl", functionUrl);
		Page<GenericFunction> data = functionService.findFunctionByNameOrUrl(conditionMap,pageable);
		
		for(GenericFunction f : data.getContent()) {
			if(f.getStatus().equals("1")) {
				f.setStatus("有效");
			}
			if(f.getStatus().equals("0")) {
				f.setStatus("无效");
			}
			if("MENU".equals(f.getType())) {
				f.setType("菜单");
			}
			if("URL".equals(f.getType())) {
				f.setType("URL资源");
			}
		}
		resultMap.put("totalCount", data.getTotalElements());
		resultMap.put("resultList", data.getContent());
		return resultMap;
	}
	
	
	@RequestMapping(value = "/createFunctionData", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> createFunctionData(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		String name = (String)pageRequest.get("name_create");
		String url = (String)pageRequest.get("url_create");
		String type = (String)pageRequest.get("type_create");
		String code = (String)pageRequest.get("code_create");
		String status = (String)pageRequest.get("status_create");
		
		GenericFunction function = new GenericFunction();
		if(!StringUtils.isBlank(name)) {
			function.setName(name);
		}
		if(!StringUtils.isBlank(url)) {
			function.setUrl(url);
		}
		if(!StringUtils.isBlank(type)) {
			function.setType(type);
		}
		if(!StringUtils.isBlank(code)) {
			function.setCode(code);
		}
		if(!StringUtils.isBlank(status)) {
			function.setStatus(status);
		}
		List<GenericFunction> functions = functionService.findMenuFuncionByCode(function.getCode());
		
		if(functions.size()>0) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, "功能代码重复!");
			return resultMap;
		}
		
		try {
			functionService.save(function);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryMenus(model,request,pageRequest);
	}
	
	@RequestMapping(value = "/updateFunctionDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> updateFunctionDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		Long id = Long.parseLong((String)pageRequest.get("id_update"));
		String name = (String)pageRequest.get("name_update");
		String url = (String)pageRequest.get("url_update");
		String type = (String)pageRequest.get("type_update");
		String code = (String)pageRequest.get("code_update");
		String status = (String)pageRequest.get("status_update");
		
		GenericFunction function = new GenericFunction();
		function.setId(id);
		if(!StringUtils.isBlank(name)) {
			function.setName(name);
		}
		if(!StringUtils.isBlank(url)) {
			function.setUrl(url);
		}
		if(!StringUtils.isBlank(type)) {
			function.setType(type);
		}
		if(!StringUtils.isBlank(code)) {
			function.setCode(code);
		}
		if(!StringUtils.isBlank(status)) {
			function.setStatus(status);
		}
		List<GenericFunction> functions = functionService.findMenuFuncionByCode(function.getCode());
		
		if(functions.size()>0) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, "功能代码重复!");
			return resultMap;
		}
		
		try {
			functionService.save(function);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryMenus(model,request,pageRequest);
	}
	
	@RequestMapping(value = "/deleteFunctionDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> deleteFunctionDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		String id = (String)pageRequest.get("id");
		
		GenericFunction function ;
		
		if(StringUtils.isNotBlank(id)) {
			//选择单条数据时
			if(!id.contains(",")) {
				function = new GenericFunction();
				function.setId(Long.parseLong(id));
				GenericFunction function1 = functionService.findFunctionByFunctionId(Long.parseLong(id));
				MenuEO m = menuService.findMenuByCode(function1.getCode());
				if(m!=null) {
					resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
					resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, "请先删除对应的菜单!");
					return resultMap;
				}
				functionService.deleteFuncion(function);
			}else {
				String [] idArr = id.split(",");
				for(String idx :idArr) {
					GenericFunction gf = new GenericFunction();
					gf.setId(Long.parseLong(idx));
					GenericFunction function1 = functionService.findFunctionByFunctionId(Long.parseLong(idx));
					MenuEO m = menuService.findMenuByCode(function1.getCode());
					if(m!=null) {
						resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
						resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, "请先删除对应的菜单!");
						return resultMap;
					}
					functionService.deleteFuncion(gf);
				}
			}
		}
		
		return queryMenus(model,request,pageRequest);
	}
	
	@RequestMapping(value = "/searchFunctionDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public GenericFunction searchFunctionDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Long id = Long.parseLong((String)pageRequest.get("id"));
		GenericFunction function = functionService.findFunctionByFunctionId(id);
		return function;
		
	}
	
}