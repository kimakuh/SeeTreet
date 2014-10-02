package foo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DoFilter implements Filter{
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println(">> Filter √ ±‚»≠");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("req...");
		req.setCharacterEncoding("UTF-8");
		chain.doFilter(req, res);
		System.out.println("res...");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}	
}
