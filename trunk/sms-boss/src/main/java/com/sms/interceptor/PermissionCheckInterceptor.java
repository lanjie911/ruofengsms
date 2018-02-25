package com.sms.interceptor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sms.entity.manager.Permission;



public class PermissionCheckInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object handler) throws Exception {

		HttpSession session = req.getSession(false);
		if (session == null) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}

		boolean find = true;
		String curPath = req.getServletPath();
		List<Permission> permissions = (List<Permission>) session.getAttribute("USER_PERMISSIONS");
		
		outer:
		for (Permission p : permissions) {
			String urlFilter = p.getUrlFilter().trim();
			if (StringUtils.isNotBlank(urlFilter)) {
				String[] subFilters = urlFilter.split(";|,");
				for (String subFilter : subFilters) {
					Pattern pat = Pattern.compile(subFilter, Pattern.CASE_INSENSITIVE);
					Matcher mat = pat.matcher(curPath);
					if (mat.find()) {
						find = true;
						break outer;
					}
				}	
			}
		}
		
		if (find) {
			return true;
		} else {
			res.getWriter().print("403——没有操作权限，请于管理员联系");
			return false;
		}
	}
	
	public static void main(String[] args) {
		
		boolean find = true;
		String curPath = "/htm/mobileoperator/list.htm";
		String urlFilter = "/htm/mobileoperator/list.htm,/htm/mobileoperator/view.htm";
		String[] subFilters = urlFilter.split(";|,");
		for (String subFilter : subFilters) {
			Pattern pat = Pattern.compile(subFilter, Pattern.CASE_INSENSITIVE);
			Matcher mat = pat.matcher(curPath);
			if (mat.find()) {
				find = true;
				break;
			}
		}	
	}

}
