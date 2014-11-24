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
@WebServlet("/admin/map/*")
public class MapinfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String PREFIX = "/admin/map/";
    public final String ADDTOCOORD = "/admin/map/addtocoord";
    public final String COORDTOADD = "/admin/map/coordtoadd";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapinfoController() {
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
		PrintWriter out = res.getWriter();
		System.out.println("> SERVLET : " + PREFIX);
		try{
			if(cmd.contains(ADDTOCOORD)){
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, convertAddToCoord(req, res)));	
			}
			else if(cmd.contains(COORDTOADD)){
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, convertCoordToAdd(req, res)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(out != null) out.close();
		}
	}
	
	private JSONArray convertAddToCoord(HttpServletRequest req , HttpServletResponse res) throws IOException {
		String Address = (String)req.getParameter("address");
		return HttpCall.getAddToCoord(Address);
	}
	private JSONObject convertCoordToAdd(HttpServletRequest req , HttpServletResponse res) throws IOException {
		String longitude = (String)req.getParameter("longitude");
		String latitude = (String)req.getParameter("latitude");
		return HttpCall.getCoordToAdd(longitude, latitude);
	}
	
	

}
