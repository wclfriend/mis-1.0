package cn.com.cs.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(URLFilter.class);

	/**
	 * 需要排除的页面
	 */
	private String excludedPages;

	public String[] excludedPageArray;

	@SuppressWarnings("unused")
	private FilterConfig filterConfig;
	
	// XSS处理Map
	public static Map<String,String> xssMap = new LinkedHashMap<String,String>();
     
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		excludedPages = filterConfig.getInitParameter("excludedPages")==null?"":filterConfig.getInitParameter("excludedPages");
		if (StringUtils.isNotEmpty(excludedPages)) {
			excludedPageArray = excludedPages.replaceAll("[\\s]", "").split(",");
		}else{
			excludedPageArray = new String[]{""};
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		long startTime = System.currentTimeMillis();
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		String requestURI = httpRequest.getRequestURI();
		if (requestURI.indexOf("/static/") > 0
				|| requestURI.indexOf("/lib/") > 0
				|| requestURI.indexOf("/resources/") > 0) {
			
			chain.doFilter(request, response);
		} else {
			logger.info(requestURI + "----------------start----------------");
			logger.info("request URL: " + httpRequest.getRequestURL());
			logger.info("request queryStr: "+httpRequest.getQueryString());
			logger.info("method: " + httpRequest.getMethod());
	 		logger.info("remote Addr: " + httpRequest.getRemoteAddr());
			chain.doFilter(request, response);  
			
	        long endTime = System.currentTimeMillis();
			StringBuffer logInfo = new StringBuffer("Time consuming: [").append((endTime - startTime)).append(" ms]");
		    	logger.info(logInfo.toString());
		    	logger.info(requestURI + "----------------end----------------");
		}
	}

	public void destroy() {
		this.filterConfig = null;
	}
}
