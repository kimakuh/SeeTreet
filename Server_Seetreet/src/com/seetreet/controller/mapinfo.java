package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.DBObject;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.content.ContentProviderBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.http.HttpCall;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class mapinfo
 */
@WebServlet("/admin/mapinfo")
public class mapinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mapinfo() {
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
		req.setCharacterEncoding("utf-8");
		PrintWriter out = res.getWriter();
		System.out.println("> SERVLET : Map Info");
		try{
			out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, convertMapXY(req, res)));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(out != null) out.close();
		}
	}
	
	private JSONArray convertMapXY(HttpServletRequest req , HttpServletResponse res) throws IOException {
		String Address = (String)req.getParameter("address");
		return HttpCall.getMapInfo(Address);
	}
	

}
