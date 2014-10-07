package com.seetreet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.seetreet.bean.GenreBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.content.ContentProviderBean;

/**
 * Servlet implementation class ContentProviderController
 */
@WebServlet("/user/content/provider/*")
public class ContentProviderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String PREFIX = "/user/content/provider/";
    public final String ENROLL = "/user/content/provider/enroll/";
    public final String SEARCH = "/user/content/provider/search/";
    public final String UPDATE = "/user/content/provider/update/";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentProviderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String cmd = reqURI.substring(contextPath.length());
		
		System.out.println("> SERVLET : " + PREFIX);
		
		if(cmd.contains(ENROLL)) {
			System.out.println(">> enroll");
		}else if(cmd.contains(SEARCH)) {
			System.out.println(">> search");
		}else if(cmd.contains(UPDATE)) {
			System.out.println(">> update");
		}
		
	}

	private void enrollContentByProvider(HttpServletRequest req , HttpServletResponse res) {
		String token = req.getHeader(UserBean.KEY_TOKEN);
		String email = (String) req.getAttribute(UserBean.KEY_EMAIL);
		String body = req.getParameter("data");
		
		
	}
	
}
