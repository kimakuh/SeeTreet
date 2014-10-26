package com.seetreet.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.rmi.server.Dispatcher;

import com.seetreet.bean.UserBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class ContentController
 */
@WebServlet("/user/content/*")
public class ContentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PREFIX = "/user/content/";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentController() {
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
		
		String postfix = cmd.replace(PREFIX, "");
		
		System.out.println("> SERVLET : " + PREFIX);
		RequestDispatcher dispatcher = null;
		if(postfix.contains(ContentProviderController.PREFIX)) {
			dispatcher = req.getRequestDispatcher(ContentProviderController.PREFIX+"*");
		} else if(postfix.contains(ContentUserController.PREFIX)){
			System.out.println(">> user content");
			dispatcher = req.getRequestDispatcher(ContentUserController.PREFIX+"*");
		}
		
		System.out.println(">> ContentController :: "+postfix);
		
		dispatcher.forward(req, res);					
	}

}
