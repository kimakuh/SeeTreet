package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seetreet.bean.ArtistBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.bean.UserBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.dao.MongoPersonDAO;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class PersonController
 */
@WebServlet("/user/person/*")
public class PersonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String PREFIX_IS_USER	= "/user/person/check/user/";
	private final String PREFIX_IS_ARTIST		= "/user/person/check/artist/";
	private final String PREFIX_IS_PROVIDER		= "/user/person/check/provider/";
	
	private final String PREFIX_GET_USER		= "/user/person/get/user/";
	private final String PREFIX_GET_ARTIST		= "/user/person/get/artist/";
	private final String PREFIX_GET_PROVIDER	= "/user/person/get/provider/";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonController() {
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
		
		String email = (String)req.getAttribute(UserBean.KEY_EMAIL);
		String token = req.getHeader(UserBean.KEY_TOKEN);
		
		PrintWriter out = res.getWriter();
		try {
			if(cmd.contains(PREFIX_IS_USER)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoDAO.hasUser(email)));
			} else if(cmd.contains(PREFIX_IS_ARTIST)) {		
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoPersonDAO.isArtist(email, token)));
			} else if(cmd.contains(PREFIX_IS_PROVIDER)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoPersonDAO.isProvider(email, token)));
			} else if(cmd.contains(PREFIX_GET_USER)) {
				String userEmail = req.getParameter(UserBean.KEY_EMAIL);
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoPersonDAO.getUser(userEmail)));
			} else if(cmd.contains(PREFIX_GET_ARTIST)) {
				String artistId = req.getParameter(ArtistBean.KEY_ID);
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoPersonDAO.getArtist(artistId)));
			} else if(cmd.contains(PREFIX_GET_PROVIDER)) {
				String providerId = req.getParameter(ProviderBean.KEY_ID);
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoPersonDAO.getProvider(providerId)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_DB, null));
			} catch (Exception e2) {
				// TODO: handle exception
				e.printStackTrace();
			}			
			e.printStackTrace();
		} finally {
			if(out!=null) out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
