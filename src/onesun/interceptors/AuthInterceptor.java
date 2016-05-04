package onesun.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onesun.model.SessionInfo;
import onesun.util.RequestUtil;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录拦截器
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger
			.getLogger(AuthInterceptor.class);

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		String reqUrl = RequestUtil.getRequestPath(request);
		if (reqUrl.contains("/userCtr.do?login")) {
			return true;
		}
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute("sessionInfo");
		if (sessionInfo != null && sessionInfo.getIfLogin()) {
			return true;
		}
		response.sendError(577, "未登录或登录超时");
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}
}
