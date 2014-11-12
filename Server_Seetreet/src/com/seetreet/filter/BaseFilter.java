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

import com.seetreet.util.C;

/**
 * Servlet Filter implementation class BaseFilter
 */
@WebFilter("/*")
public class BaseFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BaseFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		req.setCharacterEncoding(C.ENCODING);
		res.setCharacterEncoding(C.ENCODING);
		
		HttpServletResponse httpRes = (HttpServletResponse)res;
		httpRes.setHeader("Access-Control-Allow-Origin", "*");
		httpRes.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		httpRes.setHeader("Access-Control-Allow-Headers", "*");	
		
		HttpServletRequest hr = (HttpServletRequest) req;		
		
		// pass the request along the filter chain
		chain.doFilter(req, res);
		
			
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
