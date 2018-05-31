package cn.com.cs.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.common.util.MD5Util;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.system.pojo.base.user.SystemUser;
import cn.com.cs.system.service.SystemMenuService;
import cn.com.cs.system.service.SystemRoleService;
import cn.com.cs.system.service.SystemUserService;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.user.pojo.WechatUserInfo;

@Scope("prototype")
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
	
	@Autowired
	SystemUserService systemUserService;
	
	@Autowired
	SystemMenuService systemMenuService;
	
	@Autowired
	SystemRoleService systemRoleService;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Page list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true, value = "start", defaultValue = "0") int start,
			@RequestParam(required = true, value = "length", defaultValue = "10") int length) {
		Page page = new Page();
		page.setStart(start);
		page.setLength(length);
		List<WechatUserInfo> list = systemUserService.list(WechatUserInfo.class, page);
		
		page.setData(list);
		page.setDraw(StringUtils.isBlank(request.getParameter("draw")) ? 1 : Integer.valueOf(request.getParameter("draw")) + 1);
		
		return page;
	}
	@RequestMapping(value="/detail")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/web/system/user/user_detail");
		
		SystemUser user = systemUserService.findUniqueByProperty(SystemUser.class, "userName", request.getParameter("userName"));
		mav.addObject("user", user);
		
		return mav;
	}

	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,SystemUser systemUser) {
		ModelAndView mav = new ModelAndView("/web/system/user/user_edit");
		
		systemUser = systemUserService.findUniqueByProperty(SystemUser.class, "id", request.getParameter("id"));
		
		return mav.addObject("systemUser", systemUser);
	}
	
	@RequestMapping(value="/update", method={RequestMethod.POST})
	public void update(HttpServletRequest request, HttpServletResponse response, SystemUser systemUser) {
		System.out.println("id:" + systemUser.getId());
		SystemUser user_tar = systemUserService.getEntity(SystemUser.class, systemUser.getId());
		if (user_tar == null) {
			user_tar = new SystemUser();
			user_tar.setPassword(MD5Util.MD5("111111"));
		}
		user_tar.setUserName(systemUser.getUserName());
		user_tar.setRealName(systemUser.getRealName());
//		SystemRole role = systemRoleService.findUniqueByProperty(SystemRole.class, "roleCode", systemUser.getRole().getRoleCode());
//		user_tar.setRole(role);
		
		if (StringUtils.isNotBlank(user_tar.getId())) {
			systemUserService.update(user_tar);
		} else {
			systemUserService.save(user_tar);
		}
	}
	
}
