package com.seetreet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seetreet.bean.UserBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.C;

@WebFilter("*.see")
public class SessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
//		HttpServletRequest httpReq = (HttpServletRequest)req;
//		HttpServletResponse httpRes = (HttpServletResponse)res;
//		req.setCharacterEncoding(C.ENCODING);		
//		res.setCharacterEncoding(C.ENCODING);
//		
//		String uri = httpReq.getRequestURI();
//		String contextPath = httpReq.getContextPath();
//		String cmd = uri.substring(contextPath.length());
//		
//		HttpSession sess = httpReq.getSession();
//		String token = (String) sess.getAttribute(UserBean.KEY_TOKEN);
//		String email = (String) sess.getAttribute(UserBean.KEY_EMAIL);
//		
//		System.out.println(">> session Filter :: " + cmd + " , " + token + " , " + email);
//		if(!MongoDAO.isUser(email, token) && !cmd.equals("/hello.see")) {
//			httpRes.sendRedirect("hello.see");
//			return;
//		}		
		
		chain.doFilter(req, res);	
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
