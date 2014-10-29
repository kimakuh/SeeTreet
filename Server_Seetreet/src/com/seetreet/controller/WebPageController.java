package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class WebPageController
 */
@WebServlet("*.see")
public class WebPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = req.getRequestURI();
		System.out.println(">>URI : "+uri);
		String contextPath = req.getContextPath();
		System.out.println(">>contextPath : "+contextPath);
		String cmd = uri.substring(contextPath.length());
		System.out.println(">> WebPageController :: " + cmd);
		RequestDispatcher dispatcher = null;
		if(cmd.equals("/hello.see")) {

			dispatcher = req.getRequestDispatcher("/html/index.html");	

		}
		
		dispatcher.forward(req, res);
	}
}
