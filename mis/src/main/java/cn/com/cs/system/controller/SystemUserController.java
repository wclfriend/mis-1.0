
package cn.com.cs.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
//import org.jeecgframework.minidao.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.util.MD5Util;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.system.pojo.base.role.SystemRole;
import cn.com.cs.system.pojo.base.user.SystemUser;
import cn.com.cs.system.service.SystemMenuService;
import cn.com.cs.system.service.SystemRoleService;
import cn.com.cs.system.service.SystemUserService;

@Scope("prototype")
@RestController
@RequestMapping("/user")
public class SystemUserController extends BaseController {
	
	@Autowired
	SystemUserService userService;
	
	@Autowired
	SystemMenuService menuService;
	
	@Autowired
	SystemRoleService roleService;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Page list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true, value = "start", defaultValue = "0") int start,
			@RequestParam(required = true, value = "length", defaultValue = "10") int length) {
		Page page = new Page();
		page.setStart(start);
		page.setLength(length);
		List<SystemUser> list = userService.list(SystemUser.class, page);
		
		page.setData(list);
		page.setDraw(StringUtils.isBlank(request.getParameter("draw")) ? 1 : Integer.valueOf(request.getParameter("draw")) + 1);
		
		menuService.saveLog(request, "查询用户列表");
		
		return page;
	}
	
	@RequestMapping(method={RequestMethod.GET}, value="/detail/{userName}")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response,@PathVariable String userName) {
		ModelAndView mav = new ModelAndView("/web/system/user/user_detail");
		
		SystemUser user = userService.findUniqueByProperty(SystemUser.class, "userName", userName);
		mav.addObject("user", user);
		
		menuService.saveLog(request, "显示用户详情：" + user.getUserName());
		
		return mav;
	}

	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("systemUser")SystemUser user) {
		ModelAndView mav = new ModelAndView("/web/system/user/user_edit");
		user = userService.findUniqueByProperty(SystemUser.class, "id", request.getParameter("id"));
		
		List<SystemRole> roles = roleService.getUserRoles(user.getId());
		StringBuffer roleCodes = new StringBuffer();
		for (SystemRole role : roles) {
			roleCodes.append(role.getRoleCode()).append("#");
		}
		if (roleCodes.toString().endsWith("#")) {
			roleCodes = new StringBuffer(roleCodes.substring(0, roleCodes.length() - 1));
		}

		menuService.saveLog(request, "去更新用户：" + user.getUserName());
		
		return mav.addObject("systemUser", user).addObject("roleCodes", roleCodes.toString());
	}
	
	@RequestMapping(value="exist", method={RequestMethod.GET})
	public AjaxJson exist(@RequestParam(value="userName")String userName,HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		SystemUser user = userService.getUser(userName);
		String id = request.getParameter("id");
		
		if (user == null) {
			json.setSuccess(true);
		} else {
			menuService.saveLog(request, "验证用户名是否已重复：" + user.getUserName());
			if (id.equals(user.getId())) {
				json.setSuccess(true);
			} else {
				json.setSuccess(false);
				json.setMsg("用户名已存在");
			}
		}
		
		return json;
	}
	
	@RequestMapping(value="update", method={RequestMethod.POST})
	public void update(HttpServletRequest request, HttpServletResponse response, SystemUser systemUser) {
		AjaxJson json = new AjaxJson();
		SystemUser user = userService.getEntity(SystemUser.class, systemUser.getId());
		if (user == null) {
			user = new SystemUser();
			user.setPassword(MD5Util.MD5("111111"));
		}
		user.setUserName(systemUser.getUserName());
		user.setRealName(systemUser.getRealName());
		if ("superAdmin".equals(user.getUserName())) {
			json.setSuccess(false);
			json.setMsg("不可更新管理员账户信息");
			return;
		}

		try {
			Thread.sleep(1000 * 20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 user = userService.getEntity(SystemUser.class, systemUser.getId());
		 System.out.println("s:"+user.getRealName());
		
		userService.updateUserInfo(request, user);
		menuService.saveLog(request, "更新用户信息：" + user.getUserName());
	}
	
	@RequestMapping(value="testUpdate", method={RequestMethod.GET})
	public void testUpdate(HttpServletRequest request, HttpServletResponse response, SystemUser systemUser) {
		SystemUser user = userService.getEntity(SystemUser.class, "000000005f1495d7015f149738be000a");
		user.setRealName("哈哈");
		userService.saveOrUpdate(user);
	}

	@RequestMapping(method={RequestMethod.GET}, value="/lock/{id}")
	public AjaxJson lock(@PathVariable String id, HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		SystemUser user = userService.get(SystemUser.class, id);
		if (user == null) {
			json.setSuccess(false);
			json.setMsg("ID有误");
			return json;
		}
		if (user.isLockFlag()) {
			user.setLockFlag(false);
			json.setMsg("账号已锁定");
			menuService.saveLog(request, "锁定用户账号：" + user.getUserName());
		} else {
			user.setLockFlag(true);
			json.setMsg("解锁成功");
			menuService.saveLog(request, "解锁用户账号：" + user.getUserName());
		}
		userService.update(user);
		
		
		return json;
	}
	
	@RequestMapping(method={RequestMethod.GET}, value="/del/{id}")
	public AjaxJson del(@PathVariable String id, HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		SystemUser user = userService.getEntity(SystemUser.class, id);
		if (user == null) {
			json.setSuccess(false);
			json.setMsg("ID有误");
			return json;
		}
		if (user.getUserName().matches("superAdmin|admin")) {
			json.setSuccess(false);
			json.setMsg("管理员账户不可删除");
			return json;
		}
		
		menuService.saveLog(request, "删除用户账号：" + user.getUserName());
		
		boolean flag = userService.deleteSystemUser(user);
		json.setSuccess(flag);
		
		return json;
	}
	
	@RequestMapping(method={RequestMethod.POST}, value="reset")
	public AjaxJson reset(HttpServletRequest request,@RequestParam String id) {
		AjaxJson json = new AjaxJson();
		SystemUser user = userService.getEntity(SystemUser.class, id);
		if (user == null) {
			json.setSuccess(false);
			json.setMsg("ID有误");
			return json;
		}
		user.setPassword(MD5Util.MD5(request.getParameter("newpassword")));
		
		userService.update(user);
		
		menuService.saveLog(request, "重置用户密码：" + user.getUserName());
		
		return json;
	}
	
}



















