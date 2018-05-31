package cn.com.cs.core.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cn.com.cs.system.pojo.base.user.SystemUser;
import cn.com.cs.system.service.SystemUserService;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private String defaultFailureUrl;

	@Autowired
	SystemUserService systemUserService;
	
	@Autowired
	AuthenticationSuccessHandler successHandler;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("请输入用户名");
		}
		if (StringUtils.isBlank(password)) {
			throw new UsernameNotFoundException("请输入密码");
		}
		
		SystemUser systemUser = systemUserService.checkLogin(username, "");
		if (systemUser == null)
			throw new UsernameNotFoundException("用户名不存在");
		
		if (!systemUser.isLockFlag()) {
			throw new UsernameNotFoundException("账户已停用，不可使用");
		}
		
		SystemUser user = systemUserService.checkLogin(username, password);
		if (user == null) {
			throw new LoginUserException("用户名或密码错误");
		}
		
		Authentication authentication = super.attemptAuthentication(request, response);
		
		return authentication;
	}

	/**
	 * 重写登录失败处理方法
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication request failed: " + failed.toString(), failed);
			logger.debug("Updated SecurityContextHolder to contain null Authentication");
		}

		saveException(request, failed);

		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
	

	/**
	 * 重写登录成功方法
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		
		SecurityContextHolder.getContext().setAuthentication(authResult);
		
		LoginUserInfo loginUser = (LoginUserInfo) authResult.getPrincipal();

		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}

		request.getSession().setAttribute("loginUser", "");

		successHandler.onAuthenticationSuccess(request, response, authResult);
	}


	protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
		request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}
}
