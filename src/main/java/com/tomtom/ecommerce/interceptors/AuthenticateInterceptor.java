package com.tomtom.ecommerce.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tomtom.ecommerce.exceptions.EcommerceException;

public class AuthenticateInterceptor implements HandlerInterceptor {

	Logger logger = LogManager.getLogger(AuthenticateInterceptor.class);

	final String USER_ID = "usrId";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		logger.info("checking session interceptor pre handler, requested url : " + request.getRequestURL());
		String userId = request.getHeader(USER_ID); // get from header

		// setting default to 2 for local dev testing purpose
		userId = "2";

		if (userId == null || userId.isEmpty() || (Long.parseLong(userId) < 1l)) {
			throw new EcommerceException("Invalid User session", HttpStatus.UNAUTHORIZED);
		}

		// authentication and authorization for particular resource can be done here in
		// this method

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		logger.info("postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		logger.info("afterCompletion");
	}
}
