package com.love.family.controller.UserManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.love.family.business.userManage.entity.UserInfo;
import com.love.family.business.userManage.service.UserManageService;
import com.love.family.utils.MessageUtil;

@Controller
@RequestMapping("/userManage")
public class UserManageController {
	
	@Autowired
	UserManageService userManageService;
	/**
	 * 用户登陆
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping(value="/findUserInfoList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findUserInfoList(HttpServletRequest request,@RequestParam Map<String,Object> pageRequest) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<UserInfo> userInfoList = userManageService.findUserInfoList(pageRequest);
			resultMap.put("userInfoList", userInfoList);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
		}
		return resultMap;
	}
}
