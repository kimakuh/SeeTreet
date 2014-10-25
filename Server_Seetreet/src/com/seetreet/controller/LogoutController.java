package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.UserLoginBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/user/logout/*")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PREFIX = "/user/logout/";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String cmd = reqURI.substring(contextPath.length());
		
		String email = cmd.replace(PREFIX, "");		
		String token = req.getHeader(UserBean.KEY_TOKEN);	
		
		
		PrintWriter out = res.getWriter();
		try {			
			if(MongoDAO.isUser(email, token)) {
				HttpSession sess = null;
				if((sess = req.getSession(false)) != null) {
					sess.invalidate(); // 로그아웃! 모든 세션 삭제.				
				}			
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, new JsonObject()));
			}else {
				out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_UNKNOWN_TOKEN, new JsonObject()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out != null) out.close();
		}				
	}

}
