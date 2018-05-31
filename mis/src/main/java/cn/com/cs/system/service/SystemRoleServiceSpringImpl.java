package cn.com.cs.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.system.pojo.base.menu.SystemRoleMenu;
import cn.com.cs.system.pojo.base.role.SystemRole;
import cn.com.cs.system.pojo.base.role.SystemRoleUser;

@Service("systemRoleService")
//@Transactional
public class SystemRoleServiceSpringImpl extends BaseServiceImpl implements SystemRoleService {

	@Override
	public SystemRole getUser(String username) {
		String hql = "from " + SystemRole.class.getSimpleName() + " where username = ? ";
		
		List<SystemRole> list = super.queryByHql(hql, username);
		
		return list.isEmpty() ? null : list.get(0);
	}
	
	@Override
	public List<SystemRole> getUserRoles(String userId) {
		StringBuffer hql = new StringBuffer("from ").append(SystemRole.class.getSimpleName()).append(" where id in (select role.id from ").append(SystemRoleUser.class.getSimpleName()).append(" where userId  = ?)");
		
		List<SystemRole> list = super.queryByHql(hql.toString(), userId);
		
		return list;
	}
	
	@Override
	public Map<String, String> getRoels() {
		Map<String, String> result = new HashMap<String, String>();
		String sql = "select roleCode,roleName from sys_role";
		
		List<Map<String, Object>> rols = super.queryForList(sql);
		
		for (Map<String, Object> list : rols) {
			result.put(list.get("roleCode").toString(), list.get("roleName").toString());
		}
		
		return result;
	}

	@Override
	public void updateRoleMenu(String roleId, String menuId) {
		String hql = "delete from sys_role_menu where roleId = ?";
		Integer deleteResult = super.executeSql(hql, roleId);
		System.out.println(deleteResult);
		
		String[] id = menuId.split("#");
		SystemRoleMenu roleMenu = null;
		for (String str : id) {
			roleMenu = new SystemRoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(str);
			
			super.dao.save(roleMenu);
		}
	}

	@Override
	public void deleteRole(AjaxJson json, SystemRole role) {
		String hql = "from " + SystemRoleUser.class.getSimpleName() + " where role = ?";
		List<Object> roles = queryByHql(hql, role);
		if (roles.isEmpty()) {
			delete(role);
			json.setSuccess(true);
		} else {
			json.setSuccess(false);
			json.setMsg("存在该角色的用户，无法删除");
		}
		
	}

	@Override
	public void updateRoleInfo(HttpServletRequest request, SystemRole systemRole) {
		SystemRole role1 = new SystemRole();
		role1.setRoleName("roleCode");
		role1.setRoleCode("roleName");
		
		saveOrUpdate(role1);
		
		String sql = "update sys_role set roleCode='ss' where id = 'role001'";
		
		executeSql(sql);
		
		SystemRole role = getEntity(SystemRole.class, systemRole.getId());
		if (role == null) {
 			role = new SystemRole();
		}
		role.setRoleName(systemRole.getRoleName());
		role.setRoleCode(systemRole.getRoleCode());
		
		saveLog(request, "更新基本角色：" + role.getRoleName());
		
		saveOrUpdate(role);
		
	}
}















