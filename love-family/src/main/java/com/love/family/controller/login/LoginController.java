package com.love.family.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.love.family.login.service.LoginService;
import com.love.family.utils.MessageUtil;

@Controller
@RequestMapping("/")
public class LoginController {
	@Autowired
	LoginService loginService;
	/**
	 * 用户登陆
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request,@RequestParam Map<String,Object> pageRequest) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			boolean isLoginSuccess = loginService.login(pageRequest);
			if(isLoginSuccess){
				resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
				resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, "用户登陆成功。");
			}else{
				resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
				resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, "用户名和密码不匹配，请确认后重新登陆。");
			}
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/addLoginUser")
	@ResponseBody
	public Map<String, Object> addLoginUser(HttpServletRequest request,@RequestParam Map<String,Object> pageRequest) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN,MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		try {
			boolean isAddLoginUserSuccess = loginService.addLoginUser(pageRequest);
			if(isAddLoginUserSuccess){
				resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
				resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, "用户注册成功。");
			}else{
				resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
				resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, "用户注册失败。");
			}
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
		}
		
		return resultMap;
	}

}
