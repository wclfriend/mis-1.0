package cn.com.cs.system.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.system.pojo.base.menu.SystemMenu;
import cn.com.cs.system.service.SystemMenuService;

@Scope("prototype")
@RestController
@RequestMapping("/login")
public class UserLoginController extends BaseController {
	
	@Autowired
	SystemMenuService menuService;
	
	@RequestMapping(value="succ",method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index");
		
		List<SystemMenu> menus = menuService.getUserMenus();
		Map<SystemMenu, List<SystemMenu>> result = new TreeMap<SystemMenu, List<SystemMenu>>(new Comparator<SystemMenu>() {
			@Override
			public int compare(SystemMenu o1, SystemMenu o2) {
				return o1.getSortOrder().compareTo(o2.getSortOrder());
			}
		});
		
		List<SystemMenu> list = null;
		for (SystemMenu menu : menus) {
			if (menu.getParentMenu().getMenuName().equals("根节点"))
				continue;
			list = result.get(menu.getParentMenu());
			if (list == null) {
				list = new ArrayList<SystemMenu>();
			}
			list.add(menu);
			result.put(menu.getParentMenu(), list);
		}
		menuService.saveLog(request, "登录成功");
		
		return mav.addObject("menus", result);
	}
	

	
	@RequestMapping(value="/failed")
	public ModelAndView failed(HttpServletRequest request) {
		System.out.println("==========================================================================================================================");
		ModelAndView mav = new ModelAndView("/web/system/login/login");
		HttpSession session = request.getSession();
		
		Exception authenticationException = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (authenticationException != null) {
			request.setAttribute("loginMessage", authenticationException.getMessage());
			StringBuffer str = new StringBuffer();
			str.append(authenticationException.getMessage());
			str.append(";用户名：").append(request.getParameter("username"));
			str.append(";密码：").append(request.getParameter("password"));
			menuService.saveLog(request, "登录失败:" + str);
		}
		
		String userAccName = (String)session.getAttribute("springSecurityName");		
		request.setAttribute("username", userAccName);
		
		
		return mav;
	}
	
	
}
