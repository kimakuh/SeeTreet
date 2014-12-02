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
import com.seetreet.dao.MongoPersonDAO;
import com.seetreet.dao.MongoRecDAO;
import com.seetreet.util.C;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class ContentUserController
 */
@WebServlet("/user/content/user/*")
public class ContentUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String PREFIX = "/user/content/user/";
    
    private static final String SEARCH = "/user/content/user/search/";
    private static final String HISTORY = "/user/content/user/history/";
    private static final String REC = "/user/content/user/rec/";
    private static final String TEST = "/user/content/user/test/";
    
    private static final String RANK_PROVIDER = "/user/content/user/rankProvider/";
    private static final String RANK_ARTIST = "/user/content/user/rankArtist/";
    
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
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String cmd = reqURI.substring(contextPath.length());
		
		if(cmd.contains(SEARCH)) {
			searchContentByLocation(req, res);
		}
		else if(cmd.contains(HISTORY)){
			searchContentByHistory(req, res);
		}else if(cmd.contains(TEST)) {
			MongoRecDAO.updateRecommandValue();
		} else if(cmd.contains(RANK_PROVIDER)) {
			rankProvider(req, res);
		}else if(cmd.contains(RANK_ARTIST)) {
			rankArtist(req, res);
		}else if(cmd.contains(REC)) {
			recTables(req, res);
		}
	}
	
	private void searchContentByLocation(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		try {
			String longStr = req.getParameter(LocationBean.KEY_LONGITUDE);
			String latStr = req.getParameter(LocationBean.KEY_LATITUDE);
			if (latStr == null || longStr == null) {
				out.write(ResBodyFactory.create(false,ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, null));
				return;
			}

			float l_long = Float.parseFloat(longStr);
			float l_lat = Float.parseFloat(latStr);
			int page = Integer.parseInt(req.getParameter("page"));

//			ContentBean[] beans = MongoDAO.getContentsByLocation(l_lat, l_long, page);
//
//			JSONArray result = new JSONArray();
//			for (ContentBean bean : beans) {
//				result.put(bean.getJson());
//			}
			
			JSONArray result = MongoDAO.getContentsByLocation(l_lat, l_long, page);
			
			out.write(ResBodyFactory.create(true,
					ResBodyFactory.STATE_GOOD_WITH_DATA, result));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	private void searchContentByHistory(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		try {
			boolean checkProviderArtist = Boolean.valueOf(req.getParameter("isProvider"));
			String _id = req.getParameter(ContentBean.KEY_ID);
			
			if (checkProviderArtist == true) {
				if(MongoPersonDAO.getProvider(_id) != null){
					out.write(ResBodyFactory.create(true,ResBodyFactory.STATE_GOOD_WITH_DATA, MongoDAO.searchContentHistory(_id, checkProviderArtist)));
				}
				else{
					out.write(ResBodyFactory.create(false,ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, null));
					return;
				}
			}
			else if(checkProviderArtist == false){
				if(MongoPersonDAO.getArtist(_id) != null){
					out.write(ResBodyFactory.create(true,ResBodyFactory.STATE_GOOD_WITH_DATA, MongoDAO.searchContentHistory(_id, checkProviderArtist)));
				}
				else{
					out.write(ResBodyFactory.create(false,ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, null));
					return;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	private void rankArtist(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		try {			
			out.write(ResBodyFactory.create(true,ResBodyFactory.STATE_GOOD_WITH_DATA, MongoDAO.rankArtist()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	private void rankProvider(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		try {			
			out.write(ResBodyFactory.create(true,ResBodyFactory.STATE_GOOD_WITH_DATA, MongoDAO.rankProvider()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	private void recTables(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		try {			
			String userId = req.getHeader(UserBean.KEY_TOKEN);
			out.write(ResBodyFactory.create(true,ResBodyFactory.STATE_GOOD_WITH_DATA, MongoRecDAO.findRecommandValue(userId)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
}
