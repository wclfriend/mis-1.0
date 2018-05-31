package cn.com.cs.system.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.common.util.MD5Util;
import cn.com.cs.common.util.ResourceUtil;
import cn.com.cs.system.pojo.base.role.SystemRole;
import cn.com.cs.system.pojo.base.role.SystemRoleUser;
import cn.com.cs.system.pojo.base.user.SystemUser;

@Service("systemUserService")
public class SystemUserServiceSpringImpl extends BaseServiceImpl implements SystemUserService {
	
	@Autowired
	SystemRoleService roleService;

	@Override
	public SystemUser getUser(String username) {
		String hql = "from " + SystemUser.class.getSimpleName() + " where username = ? ";
		
		List<SystemUser> list = super.queryByHql(hql, username);
		
		return list.isEmpty() ? null : list.get(0);
	}
	
	@Override
	public SystemUser checkLogin(String username, String password) {
		StringBuffer hql = new StringBuffer("from ").append(SystemUser.class.getSimpleName() + " where 1 = 1 ");
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isNotEmpty(username)) {
			hql.append(" and username = ?");
			list.add(username);
		}
		if (StringUtils.isNotBlank(password)) {
			hql.append(" and password = ?");
			list.add(MD5Util.MD5(password));
		}
		
		List<SystemUser> result = super.queryByHql(hql.toString(), list.toArray());
		
		return result.isEmpty() ? null : result.get(0);
	}
	
	/**
	 * 删除角色关联关系
	 * @param userId
	 */
	@Override
	public void deleteRoleUser(String userId) {
		String delSql = "delete from sys_role_user where userId = ?";
		Integer count = super.executeSql(delSql, userId);
		System.out.println("count:" + count);
	}
	
	@Override
	public boolean deleteSystemUser(SystemUser user) {
		//删除角色关联信息
		deleteRoleUser(user.getId());
		
		//删除用户
		delete(user);
		
		return true;
	}

	@Override
	public void updateUserInfo(HttpServletRequest request, SystemUser user) {
		try {
			//保存头像文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			multipartRequest.getSession().getServletContext();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			MultipartFile file = fileMap.get("headIamge");
			String rootPath = multipartRequest.getSession().getServletContext().getRealPath("/");
			String path = ResourceUtil.getConfigByName("headImagePath");
			rootPath = rootPath + path;
			File myFile = new File(rootPath);
			if (!myFile.exists()) {
				myFile.mkdirs();
			}
			String fileName = user.getUserName() + "_headImage.jpg";
			File savefile = new File(rootPath + fileName);
			FileCopyUtils.copy(file.getBytes(), savefile);
			
			user.setHeadImage(path + fileName);
		} catch (Exception e) {
			System.out.println("无头像");
		}
		saveOrUpdate(user);
		
		//删除历史角色信息
		deleteRoleUser(user.getId());
		
		//保存用户角色信息
		String[] roleCodes = request.getParameterValues("roleCode");
		SystemRoleUser roleUser = null;
		for (String roleCode : roleCodes) {
			roleUser = new SystemRoleUser();
			SystemRole role = roleService.findUniqueByProperty(SystemRole.class, "roleCode", roleCode);
			if (role != null) {
				roleUser.setRole(role);
				roleUser.setUser(user);
				
				roleService.saveOrUpdate(roleUser);
			}
		}
	}
}
