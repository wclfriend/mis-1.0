package cn.com.cs.core.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.com.cs.system.pojo.base.user.SystemUser;
import cn.com.cs.system.service.SystemUserService;

public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	SystemUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SystemUser user = userService.checkLogin(username, "");
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		GrantedAuthority auth1=new SimpleGrantedAuthority("ROLE_USER");
		GrantedAuthority auth2=new SimpleGrantedAuthority("ROLE_ADMIN");
		auths.add(auth1);
		auths.add(auth2);
		
		LoginUserInfo userInfo = new LoginUserInfo(user.getUserName(), user.getPassword(), user, true, true, true, true, auths);
		
		return userInfo;
	}

}
