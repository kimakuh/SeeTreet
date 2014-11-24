package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.seetreet.bean.GenreBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.content.ContentBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.dao.MongoRecDAO;
import com.seetreet.util.C;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class ContentLoveController
 */
@WebServlet("/user/content/love/*")
public class ContentLoveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String PREFIX_PUSH= "/user/content/love/push/";
    private static final String PREFIX_PULL= "/user/content/love/pull/";
    private static final String PREFIX_CHECK = "/user/content/love/has/";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentLoveController() {
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
		
		try {
			if(cmd.contains(PREFIX_PUSH)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, pushLove(req, res)));
			} else if(cmd.contains(PREFIX_PULL)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, pullLove(req, res)));
			} else if(cmd.contains(PREFIX_CHECK)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, hasLove(req, res)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(out!=null) out.close();
		}
		
	}

	private boolean pushLove(HttpServletRequest req, HttpServletResponse res) {
		String contentId = req.getParameter(ContentBean.KEY_ID);
		String token	 = req.getHeader(UserBean.KEY_TOKEN);
		try {
			JSONObject genre = new JSONObject(req.getParameter(ContentBean.KEY_GENRE));
			String property  = genre.getString(GenreBean.KEY_DETAIL);
			if(MongoRecDAO.hasLove(token, contentId)) return false;
			
			MongoRecDAO.loveContent(token, contentId);
			MongoRecDAO.plusUserLove(token, property);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}				
		return false;
	}

	private boolean pullLove(HttpServletRequest req, HttpServletResponse res) {
		String contentId = req.getParameter(ContentBean.KEY_ID);
		String token	 = req.getHeader(UserBean.KEY_TOKEN);
		try {
			JSONObject genre = new JSONObject(req.getParameter(ContentBean.KEY_GENRE));
			String property  = genre.getString(GenreBean.KEY_DETAIL);
			if(!MongoRecDAO.hasLove(token, contentId)) return false;
			
			MongoRecDAO.loveContent(token, contentId);
			MongoRecDAO.minusUserLove(token, property);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}				
		return false;
	}

	private boolean hasLove(HttpServletRequest req, HttpServletResponse res) {		
		String contentId = req.getParameter(ContentBean.KEY_ID);
		String token	 = req.getHeader(UserBean.KEY_TOKEN);
		
		return MongoRecDAO.hasLove(token, contentId);
	}
}
