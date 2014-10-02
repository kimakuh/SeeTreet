package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.seetreet.bean.UserBean;
import com.seetreet.dao.MongoDAO;

/**
 * Servlet implementation class JoinController
 */
@WebServlet("/user/join/*")
public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final String PREFIX = "/user/join/";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinController() {
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
		
		String 	email 	= cmd.replace(PREFIX, "");		
		String 	pw 		= req.getHeader("password");
		String 	name 	= req.getHeader("name");
		String  age		= req.getHeader("age");
		String 	phone 	= req.getHeader("phone");	
		
		UserBean bean = new UserBean(email, name, age == null? -1 : Integer.parseInt(age), phone, pw);
		
		PrintWriter out = res.getWriter();
		JsonObject json = new JsonObject();
		try {
			if (MongoDAO.signinUser(bean)) {				
				json.addProperty("state", true);
				out.write(json.toString());
			} else {				
				json.addProperty("state", false);
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
