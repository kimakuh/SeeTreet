package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.seetreet.bean.LocationBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.UserLoginBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/user/login/*")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private final String PREFIX = "/user/login/";
	
	private final String PREFIX_READYCOUNT = "/user/login/ready/";
	private final String PREFIX_COMPLETECOUNT = "/user/login/complete/";
	private final String PREFIX_10KM = "/user/login/10km/";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
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
		PrintWriter out = res.getWriter();
		
		try {
			if(cmd.contains(PREFIX_READYCOUNT)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoDAO.countUnFinishedContent()));
			}else if(cmd.contains(PREFIX_COMPLETECOUNT)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoDAO.countFinishedContent()));
			}else if(cmd.contains(PREFIX_10KM)) {
				double l_long = Double.parseDouble(req.getParameter(LocationBean.KEY_LATITUDE));
				double l_lat = Double.parseDouble(req.getParameter(LocationBean.KEY_LONGITUDE));
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoDAO.countLocationContent(l_lat,l_long)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			out.close();
		}
		
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
		String pw = req.getHeader("password");	
		
		UserLoginBean bean = MongoDAO.loginUser(email, pw);		
		PrintWriter out = res.getWriter();
				
		try {
			if(bean == null) {				
				out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, null));
			} else {	
				initSession(req, res, bean);
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, bean.getJson()));			
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	private void initSession(HttpServletRequest req, HttpServletResponse res, UserLoginBean bean) {
		HttpSession sess = req.getSession();
		sess.setAttribute(UserLoginBean.KEY_EMAIL, bean.getEmail());
		sess.setAttribute(UserLoginBean.KEY_AGE, bean.getAge());
		sess.setAttribute(UserLoginBean.KEY_NAME, bean.getName());
		sess.setAttribute(UserLoginBean.KEY_TOKEN, bean.getToken());		
	}
}
