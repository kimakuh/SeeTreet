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
		System.out.println(">> WEB-ACCESS");
		HttpServletRequest httpReq = (HttpServletRequest)req;
		HttpServletResponse httpRes = (HttpServletResponse)res;
		req.setCharacterEncoding(C.ENCODING);
		res.setContentType("text/html;");
		res.setCharacterEncoding(C.ENCODING);
		HttpSession sess = httpReq.getSession();
		if(sess.getAttribute(UserBean.KEY_TOKEN) == null) {
			httpRes.sendRedirect("hello.see");
		}
		System.out.println(">> web");
		chain.doFilter(req, res);	
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
