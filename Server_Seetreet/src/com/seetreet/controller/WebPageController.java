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
		String contextPath = req.getContextPath();
		String cmd = uri.substring(contextPath.length());		
		RequestDispatcher dispatcher = null;
		if(cmd.equals("/hello.see")) {
			dispatcher = req.getRequestDispatcher("./views/pc/pc.hello.html");
		} else if(cmd.equals("/enjoy.see")) {
			dispatcher = req.getRequestDispatcher("./views/pc/pc.index.html");
		}
		
		dispatcher.forward(req, res);
	}
}
