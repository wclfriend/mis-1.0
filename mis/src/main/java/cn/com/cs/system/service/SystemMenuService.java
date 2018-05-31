package cn.com.cs.system.service;

import java.util.List;

import cn.com.cs.common.service.BaseService;
import cn.com.cs.system.pojo.base.menu.SystemMenu;
import cn.com.cs.system.pojo.base.role.SystemRole;

public interface SystemMenuService  extends BaseService {

	SystemMenu getUser(String username);

	List<SystemMenu> getUserMenus();

	List<String> getMenusByRole(List<SystemRole> roles);

	List<String> getMenusByRoleId(String roleId);
}
