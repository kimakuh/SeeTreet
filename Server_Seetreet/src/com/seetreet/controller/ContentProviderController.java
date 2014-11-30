package com.seetreet.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBObject;
import com.seetreet.bean.ArtistBean;

import com.seetreet.bean.GenreBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.content.ContentBean;
import com.seetreet.bean.content.ContentProviderBean;

import com.seetreet.dao.MongoDAO;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class ContentProviderController
 */
@WebServlet("/user/content/provider/*")
public class ContentProviderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String PREFIX = "/user/content/provider/";
    public final String ENROLL = "/user/content/provider/enroll/";
    public final String SEARCH = "/user/content/provider/search/";
    public final String UPDATE = "/user/content/provider/update/";
    public final String DELETE = "/user/content/provider/delete/";
    public final String HISTORY = "/user/content/provider/history/";
    public final String PERMIT = "/user/content/provider/permit/";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentProviderController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String cmd = reqURI.substring(contextPath.length());
		PrintWriter out = res.getWriter();
		
		System.out.println("> SERVLET : " + PREFIX);
		try{
			if(cmd.contains(SEARCH)) {
				System.out.println(">> search Get");
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, searchContentByProvider(req, res)));
			}else if(cmd.contains(HISTORY)) {
				System.out.println(">> HISTORY Get");
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, searchHistoryByProvider(req, res)));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String cmd = reqURI.substring(contextPath.length());
		PrintWriter out = res.getWriter();
		System.out.println("> SERVLET : " + PREFIX);
		try{
			if(cmd.contains(ENROLL)) {
				System.out.println(">> enroll Post");
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, enrollContentByProvider(req, res)));
			}else if(cmd.contains(UPDATE)) {
				System.out.println(">> update Post");
			}else if(cmd.contains(DELETE)) {
				System.out.println(">> delete Post");
				boolean isDeleted = deleteContentByProvider(req, res);
				if(isDeleted) {
					out.write(ResBodyFactory.create(isDeleted, ResBodyFactory.STATE_GOOD_WITH_DATA, null));
				}else {
					out.write(ResBodyFactory.create(isDeleted, ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, null));
				}	
			}else if(cmd.contains(PERMIT)) {
				System.out.println(">> permit Post");
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, confirmArtistByProvider(req, res)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(out != null) out.close();
		}
		
		
	}
	
	private JSONObject enrollContentByProvider(HttpServletRequest req , HttpServletResponse res) throws IOException {
		String contentTitle = (String)req.getParameter(ContentProviderBean.KEY_TITLE);
		String contentStartTime = (String)req.getParameter(ContentProviderBean.KEY_STARTTIME);
		String contentEndTime = (String)req.getParameter(ContentProviderBean.KEY_ENDTIME);
		String tempPId = (String)req.getHeader(UserBean.KEY_TOKEN);
		DBObject providerObject = MongoDAO.checkProviderId(tempPId);
		if(providerObject == null)
			return null;
		//System.out.println(providerObject.toString());
		return MongoDAO.insertContentByProvider(contentTitle, contentStartTime, contentEndTime, providerObject);
	}
	
	private JSONArray searchContentByProvider(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String providerId 	= (String)req.getHeader(UserBean.KEY_TOKEN);
		int page  = Integer.parseInt(req.getParameter("page"));
		JSONArray arr = null;
		try {
			 arr = MongoDAO.searchContentByProvider(providerId , page , false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return arr;
	}
	
	private boolean deleteContentByProvider(HttpServletRequest req , HttpServletResponse res) throws IOException {
		String contentId = (String)req.getParameter(ContentBean.KEY_ID);
		return MongoDAO.deleteContentByProvider(contentId);
	}	
	
	private JSONArray searchHistoryByProvider(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String providerId 	= (String)req.getHeader(UserBean.KEY_TOKEN);
		int page  = Integer.parseInt(req.getParameter("page"));
		JSONArray arr = null;
		try {
			 arr = MongoDAO.searchContentByProvider(providerId , page , true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return arr;
	}
	
	private boolean confirmArtistByProvider(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String confirmedArtistId 	= (String)req.getParameter("artistId");
		String contentId 			= (String)req.getParameter("contentId");
		boolean state = false;
		try {
			state = MongoDAO.enrollConfirmedArtist(confirmedArtistId, contentId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return state;
	}

}
