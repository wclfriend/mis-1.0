package cn.com.cs.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.service.BaseService;
import cn.com.cs.system.pojo.base.role.SystemRole;

public interface SystemRoleService  extends BaseService {

	SystemRole getUser(String username);

	Map<String, String> getRoels();

	void updateRoleMenu(String roleId, String menuId);

	List<SystemRole> getUserRoles(String userId);

	void deleteRole(AjaxJson json, SystemRole role);

	void updateRoleInfo(HttpServletRequest request, SystemRole systemRole);

}
