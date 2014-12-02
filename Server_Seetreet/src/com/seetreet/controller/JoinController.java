package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.seetreet.bean.ArtistBean;
import com.seetreet.bean.GenreBean;
import com.seetreet.bean.LocationBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.bean.UserBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.dao.MongoPersonDAO;
import com.seetreet.util.C;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class JoinController
 */
@WebServlet("/user/join/*")
public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final String PREFIX_USER 			= "/user/join/user/";
	private final String PREFIX_ARTIST 			= "/user/join/artist/";
	private final String PREFIX_PROVIDER 		= "/user/join/provider/";
	
	
	
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
							
		PrintWriter out = res.getWriter();
		JsonObject json = new JsonObject();
		
		try {
			if(cmd.contains(PREFIX_USER)) {
				String 	email 	= cmd.replace(PREFIX_USER, "");
				if(joinUser(req, res , email)) {
					out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, true));
				}else {
					out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, false));
				}
			}else if(cmd.contains(PREFIX_ARTIST)) {
				String 	email 	= cmd.replace(PREFIX_ARTIST, "");
				if(joinArtist(req, res, email)) {
					out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, true));
				}else {
					out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, false));
				}
			}else if(cmd.contains(PREFIX_PROVIDER)) {
				String 	email 	= cmd.replace(PREFIX_PROVIDER, "");
				if(joinProvider(req, res, email)) {
					out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, true));
				}else {
					out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, false));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(out != null) out.close();
		}				
	}
	
	private boolean joinUser(HttpServletRequest req, HttpServletResponse res , String e) {
		String  email	= e;
		String 	pw 		= req.getParameter("password");
		String 	name 	= req.getParameter("name");
		String  age		= req.getParameter("age");
		String 	phone 	= req.getParameter("phone");
				
				
		UserBean bean = new UserBean(email, name, age == null? -1 : Integer.parseInt(age), phone, pw);
		
		return MongoDAO.signinUser(bean);
	}
	
	private boolean joinArtist(HttpServletRequest req, HttpServletResponse res , String e) {		
		try {
			String email = e;
			String token 		= req.getHeader(UserBean.KEY_TOKEN);
			JSONArray images 	= new JSONArray(req.getParameter(ArtistBean.KEY_IMAGES));
			String videoUrl 	= req.getParameter(ArtistBean.KEY_VIDEO);
			JSONArray genre		= new JSONArray(req.getParameter(ArtistBean.KEY_GENRE));
			String descript 	= req.getParameter(ArtistBean.KEY_DESCRIPT);
			String name 		= req.getParameter(ArtistBean.KEY_NAME);
			JSONArray locations = new JSONArray(req.getParameter(ArtistBean.KEY_LOCATIONS));			
			
			String[] encodedImages = new String[images.length()];			
			String[] imageURLs = null;
			for(int i = 0; i < images.length(); i++) {
				encodedImages[i] = images.getString(i);
			}			
			
			imageURLs = C.writeImageFileFromBase64(token, C.ADDPATH_ARTIST , C.ADDURL_ARTIST, encodedImages);		
						
			String modTime = C.currentDate();
			
			LocationBean[] locs = new LocationBean[locations.length()];
						
			for(int i = 0 ; i < locations.length(); i++) {
				JSONObject location = locations.getJSONObject(i);
				String l_descript = location.has(LocationBean.KEY_DESCRIPT)?"":location.getString(LocationBean.KEY_DESCRIPT);
				locs[i] = new LocationBean(
						location.getString(LocationBean.KEY_NAME),
						l_descript, 
						location.getDouble(LocationBean.KEY_LATITUDE), 
						location.getDouble(LocationBean.KEY_LONGITUDE));				
			}
			
			GenreBean[] genres = new GenreBean[genre.length()];
			for(int i = 0 ; i < genre.length(); i++) {
				JSONObject obj = genre.getJSONObject(i);
				genres[i] = new GenreBean(obj.getString(GenreBean.KEY_CATEGORY), obj.getString(GenreBean.KEY_DETAIL));
			}
			
			ArtistBean bean = new ArtistBean(imageURLs, name, videoUrl, descript, modTime, locs,genres);
			
			if(MongoDAO.isUser(email, token)) {
				if(MongoPersonDAO.isArtist(email, token)) {
					return false;
				}else {
					return MongoDAO.joinArtist(bean , token);
				}
			}else {
				System.out.println("not user");
				return false;
			}
		} catch (JSONException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		return false;		
	}
	
	private boolean joinProvider(HttpServletRequest req, HttpServletResponse res , String e) {
		try {
			String email = e;
			String token 		= req.getHeader(UserBean.KEY_TOKEN);
			JSONArray images 	= new JSONArray(req.getParameter(ProviderBean.KEY_IMAGES));
			JSONArray genre		= new JSONArray(req.getParameter(ProviderBean.KEY_GENRE));
			String descript 	= req.getParameter(ProviderBean.KEY_DESCRIPT);			
			JSONObject location = new JSONObject(req.getParameter(ProviderBean.KEY_LOCATION));			
			String storeTitle	= req.getParameter(ProviderBean.KEY_STORETITLE);
			String storeType	= req.getParameter(ProviderBean.KEY_STORETYPE);
			String address	 	= req.getParameter(ProviderBean.KEY_ADDRESS);

			System.out.println(req.getParameter(ProviderBean.KEY_IMAGES));
			
			String[] encodedImages = new String[images.length()];
			String[] imageURLs = null;
			for(int i = 0; i < images.length(); i++) {
				encodedImages[i] = images.getString(i);
			}
			
			imageURLs = C.writeImageFileFromBase64(token, C.ADDPATH_PROVIDER , C.ADDURL_PROVIDER , encodedImages);			
			
			String modTime = C.currentDate();
	
			double latitude = (double)location.getDouble(LocationBean.KEY_LATITUDE);
			double longitude = (double)location.getDouble(LocationBean.KEY_LONGITUDE);
			String l_descript = location.has(LocationBean.KEY_DESCRIPT)?"":location.getString(LocationBean.KEY_DESCRIPT);
			LocationBean loc = new LocationBean(
										location.getString(LocationBean.KEY_NAME), 
										l_descript, 
										latitude, 
										longitude);
						
			GenreBean[] genres = new GenreBean[genre.length()];
			for(int i = 0 ; i < genre.length(); i++) {
				JSONObject obj = genre.getJSONObject(i);
				genres[i] = new GenreBean(obj.getString(GenreBean.KEY_CATEGORY), obj.getString(GenreBean.KEY_DETAIL));
			}
			
			ProviderBean bean 
			= new ProviderBean("SEETREET", 
								imageURLs, 
								loc, 
								genres,
								storeTitle, 
								storeType, 
								descript, 
								address,
								modTime
								);
			
			if(MongoDAO.isUser(email, token)) {
				if(MongoPersonDAO.isProvider(email, token)) {
					return false;
				}else {
					return MongoDAO.joinProvider(bean , token);
				}
			}else {
				System.out.println("not user");
				return false;
			}
		} catch (JSONException ex) {
			// TODO: handle exception
			System.out.println(req.getParameter(ProviderBean.KEY_IMAGES));
			ex.printStackTrace();
		}
		return false;
	}
}
