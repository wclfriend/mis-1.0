package cn.com.cs.system.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.system.pojo.base.menu.SystemMenu;
import cn.com.cs.system.service.SystemMenuService;

@Scope("prototype")
@Controller
@RequestMapping("/menu")
public class SystemMenuController extends BaseController {
	
	@Autowired
	SystemMenuService menuService;
	
	@RequestMapping(value="/list", produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public List<SystemMenu> list(HttpServletRequest request, HttpServletResponse response) {
		Criteria criteria = menuService.getSession().createCriteria(SystemMenu.class);
		criteria.addOrder(Order.asc("sortOrder"));
		List<SystemMenu> list = criteria.list();
		
		Collections.sort(list, new Comparator<SystemMenu>() {
			@Override
			public int compare(SystemMenu o1, SystemMenu o2) {
				return o1.getSortOrder().compareTo(o2.getSortOrder());
			}
		});
		
		menuService.saveLog(request, "查询菜单列表");
		
		return list;
	}
	
	@RequestMapping(value="/getMenu")
	@ResponseBody
	public AjaxJson getMenu(HttpServletRequest request, HttpServletResponse response,@RequestParam(name="roleId")String roleId) {
		AjaxJson json = new AjaxJson();
		
		List<String> list = menuService.getMenusByRoleId(roleId);
		
		json.setObj(list);

		menuService.saveLog(request, "查询角色对应菜单列表");
		
		return json;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJson update(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson json = new AjaxJson();
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		String menuName = request.getParameter("menuName");
		String url = request.getParameter("url");
		String sortOrder = request.getParameter("sortOrder");
		String level = request.getParameter("level");
		SystemMenu menu = menuService.getEntity(SystemMenu.class, id);
		if (menu == null) {
			menu = new SystemMenu();
		} else {
			if (menu.getMenuName().equals("根节点")) {
				json.setSuccess(false);
				json.setMsg("根节点不可更新");
				
				return json;
			}
		}
		menu.setMenuName(menuName);
		menu.setUrl(url);
		menu.setLevel(level);
		menu.setSortOrder(Integer.valueOf(sortOrder));
		
		if (StringUtils.isNotBlank(parentId)) {
			SystemMenu parentMenu = menuService.getEntity(SystemMenu.class, parentId);
			menu.setParentMenu(parentMenu);
		}
		

		menuService.saveOrUpdate(menu);
		
		menuService.saveLog(request, "添加或更新菜单信息");
		
		return json;
	}
	
	
	@RequestMapping(value="/delMenu")
	@ResponseBody
	public AjaxJson delMenu(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = new AjaxJson();
		String ids = request.getParameter("id");
		String[] id = ids.split(",");
		for (String str : id) {
			SystemMenu menu = menuService.getEntity(SystemMenu.class, str);
			if (menu != null) {
				menuService.saveLog(request, "删除菜单：" + menu.getMenuName());
				menuService.delete(menu);
			} else {
				System.out.println(str);
				menuService.saveLog(request, "删除菜单失败：" + str);
			}
		}
		
		
		
		return ajaxJson;
	}
	
}
