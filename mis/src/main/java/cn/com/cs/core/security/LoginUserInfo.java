package cn.com.cs.core.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import cn.com.cs.system.pojo.base.user.SystemUser;

public class LoginUserInfo extends User {

	public SystemUser loginUser;

	private static final long serialVersionUID = 1L;
	
	public LoginUserInfo(String username, String password,SystemUser systemUser, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		
		this.loginUser = systemUser;
	}

	public SystemUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(SystemUser loginUser) {
		this.loginUser = loginUser;
	}
}
