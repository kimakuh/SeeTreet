package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.seetreet.bean.UserLoginBean;
import com.seetreet.dao.MongoDAO;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/user/login/*")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private final String PREFIX = "/user/login/";
	
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
		JsonObject json = new JsonObject();
		
		try {
			if(bean == null) {
				json.addProperty("state", false);
				out.write(json.toString());
			} else {
				System.out.println(bean.getEmail());
				json.addProperty("state", true);
				json.add("data", bean.getJson());
				out.write(json.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
