package cn.com.cs.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.system.pojo.base.role.SystemRole;
import cn.com.cs.system.service.SystemMenuService;
import cn.com.cs.system.service.SystemRoleService;

@Scope("prototype")
@RestController
@RequestMapping("/role")
public class SystemRoleController extends BaseController {

	@Autowired
	SystemRoleService roleService;
	
	@Autowired
	SystemMenuService menuService;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Page list(@RequestParam(required = true, value = "start", defaultValue = "0") int start,
			@RequestParam(required = true, value = "length", defaultValue = "10") int length) {
		Page page = new Page();
		page.setStart(start);
		page.setLength(length);
		
		List<SystemRole> list = roleService.list(SystemRole.class, page);
		
		page.setData(list);
		page.setDraw(StringUtils.isBlank(getRequest().getParameter("draw")) ? 1 : Integer.valueOf(getRequest().getParameter("draw")) + 1);
		
		menuService.saveLog(getRequest(), "查询角色列表");
		
		return page;
	}
	
	@RequestMapping(value="/show")
	@ResponseBody
	public Map<String, String> show() {
		Map<String, String> roles = roleService.getRoels();

		menuService.saveLog(getRequest(), "查询角色详情");
		
		return roles;
	}
	
	/***
	 * 更新角色的权限
	 * @param menuId
	 * @param roleId
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public void update(@RequestParam(name="menuId")String menuId,@RequestParam(name="roleId")String roleId, HttpServletRequest request) {
		menuService.saveLog(request, "更新角色权限");
		
		roleService.updateRoleMenu(roleId, menuId);
	}

	@RequestMapping(method={RequestMethod.GET}, value="/del/{id}")
	public AjaxJson del(@PathVariable String id, HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		SystemRole role = roleService.getEntity(SystemRole.class, id);
		if (role == null) {
			json.setSuccess(false);
			json.setMsg("ID有误");
			return json;
		}
		if (role.getRoleCode().equals("superAdmin")) {
			json.setSuccess(false);
			json.setMsg("管理员账户不可删除");
			return json;
		}
		menuService.saveLog(request, "删除角色：" + role.getRoleName());
		
		roleService.deleteRole(json, role);
		
		return json;
	}
	
	@RequestMapping(value="/edit/{id}", method={RequestMethod.GET})
	public ModelAndView edit(@PathVariable String id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/web/system/role/role_edit");
		SystemRole role = roleService.get(SystemRole.class, id);

		menuService.saveLog(request, "去更新角色：" + role.getRoleName());
		
		return mav.addObject("role", role);
	}

	/**
	 * 更新基本角色
	 * @param request
	 * @param response
	 * @param systemRole
	 */
	@RequestMapping(value="modify")
	public void update(HttpServletRequest request, HttpServletResponse response, SystemRole systemRole) {
		roleService.updateRoleInfo(request, systemRole);
	}
	
	@RequestMapping(value = "menus")
	public SystemRole get(HttpServletRequest request, HttpServletResponse response,@RequestParam(name="roleId")String roleId) {
		SystemRole role = roleService.get(SystemRole.class, roleId);
		
		menuService.saveLog(request, "现实角色下菜单列表：" + role.getRoleName());
		
		return role;
	}
}
