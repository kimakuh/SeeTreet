package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.seetreet.bean.LocationBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.content.ContentBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.C;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class ContentUserController
 */
@WebServlet("/user/content/user/*")
public class ContentUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String PREFIX = "/user/content/user/";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentUserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		PrintWriter out = res.getWriter();
//		
		try {			
			String longStr = req.getParameter(LocationBean.KEY_LONGITUDE);
			String latStr = req.getParameter(LocationBean.KEY_LATITUDE);
			if(latStr == null || longStr == null) {
				out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, new JsonObject()));
				return;
			}
			
			float l_long = Float.parseFloat(longStr);
			float l_lat = Float.parseFloat(latStr);
			int page	= Integer.parseInt(req.getParameter("page"));	
			
			ContentBean[] beans = MongoDAO.getContentsByLocation(l_lat, l_long , page);			
			
			JSONArray result = new JSONArray();
			for(ContentBean bean : beans) {					
				result.put(bean.getJson());
			}
			out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, result));			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(out != null) out.close();
		}	
		
				
	}
}
