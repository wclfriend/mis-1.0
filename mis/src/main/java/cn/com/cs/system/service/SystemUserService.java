package cn.com.cs.system.service;

import javax.servlet.http.HttpServletRequest;

import cn.com.cs.common.service.BaseService;
import cn.com.cs.system.pojo.base.user.SystemUser;

public interface SystemUserService  extends BaseService {

	SystemUser getUser(String username);

	SystemUser checkLogin(String username, String password);

	boolean deleteSystemUser(SystemUser user);

	void deleteRoleUser(String userId);

	void updateUserInfo(HttpServletRequest request, SystemUser user);

}
