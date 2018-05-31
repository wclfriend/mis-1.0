package cn.com.cs.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UrlInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(UrlInterceptor.class);

	/**
	 * 请求执行前
	 */
	Long startTime = null;
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		startTime = System.currentTimeMillis();
//		String uri = request.getRequestURI();
//		logger.info("["+uri+"]====================start====================");
		return true;
	}

	/**
	 * 请求执行后,视图生成前
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("请求执行后，视图生成前");
	}

	/**
	 * 请求完成后
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		String uri = request.getRequestURI();
//		logger.info("["+uri+"]==================== end ====================耗时：["+(System.currentTimeMillis() - startTime)+"]ms");
	}

}
