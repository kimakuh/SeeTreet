package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.seetreet.bean.ApiContentIdListBean;
import com.seetreet.http.HttpControl;
/**
 * Servlet implementation class FrontController
 */
@WebServlet("/api")
public class ApiReceiveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApiReceiveController() {
        super();
        // TODO Auto-generated constructor stub
    }
 protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	
    	
    	request.setCharacterEncoding("UTF-8");
    	
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//PrintWriter out = response.getWriter();
		//System.out.println("DataReceiver--------------------");
		ApiContentIdListBean beanList;
		try {
			HttpControl.getContentsId();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//HttpDAO a = new HttpDAO();
    	//a.getContentsId();
    	
    }
 }
