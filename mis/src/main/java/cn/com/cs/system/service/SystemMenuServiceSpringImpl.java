package cn.com.cs.system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.core.security.LoginUserInfo;
import cn.com.cs.core.security.SpringSecurityUtils;
import cn.com.cs.system.pojo.base.menu.SystemMenu;
import cn.com.cs.system.pojo.base.role.SystemRole;
import cn.com.cs.system.pojo.base.role.SystemRoleUser;
import cn.com.cs.system.pojo.base.user.SystemUser;

@Service("systemMenuService")
public class SystemMenuServiceSpringImpl extends BaseServiceImpl implements SystemMenuService {
	
	@Autowired
	SystemRoleService roleService;

	@Override
	public SystemMenu getUser(String username) {
		String hql = "from " + SystemMenu.class.getSimpleName() + " where username = ? ";
		
		List<SystemMenu> list = super.queryByHql(hql, username);
		
		return list.isEmpty() ? null : list.get(0);
	}


	/**
	 * 角色对应的所有菜单
	 */
	@Override
	public List<String> getMenusByRoleId(String roleId) {
		List<String> result = new ArrayList<String>();
		
		StringBuffer sql = new StringBuffer("select distinct menuId from sys_role_menu where roleId = ?");
		
		List<Map<String, Object>> list = super.queryForList(sql.toString(), roleId);
		
		for (Map<String, Object> map : list) {
			result.add(map.get("menuId").toString());
		}
		return result;
	}

	/**
	 * 角色对应的所有菜单
	 */
	@Override
	public List<String> getMenusByRole(List<SystemRole> roles) {
		StringBuffer sql = new StringBuffer("select distinct menuId from sys_role_menu where roleId in (");
		for (SystemRole systemRole : roles) {
			sql.append("'").append(systemRole.getId()).append("',");
		}
		if (sql.indexOf(",") > 0)
			sql = new StringBuffer(sql.substring(0, sql.length() - 1));
		sql.append(")");
		
		List<Map<String, Object>> list = super.queryForList(sql.toString());
		List<String> result = new ArrayList<String>();
		for (Map<String, Object> map : list) {
			result.add(map.get("menuId").toString());
		}
		return result;
	}

	@Override
	public List<SystemMenu> getUserMenus() {
		List<SystemMenu> menus = null;
		String userId = SpringSecurityUtils.getCurrentUserId();
		
		List<SystemRole> roles = roleService.getUserRoles(userId);
		List<String> menuIds = getMenusByRole(roles);
		if (menuIds.isEmpty()) {
			return Arrays.asList();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMenu.class);
		dc.add(Restrictions.in("id", menuIds));
		dc.add(Restrictions.isNotNull("parentMenu"));
		dc.addOrder(Order.asc("sortOrder"));
		
		menus = dc.getExecutableCriteria(getSession()).list();
		
		return menus;
	}

	@Autowired
	SystemRoleService systemRoleService;
//	public SystemRoleMenu getRoleMenu(String roleId,)


}
