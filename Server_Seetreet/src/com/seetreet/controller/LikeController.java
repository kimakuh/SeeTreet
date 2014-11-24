package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seetreet.bean.UserBean;
import com.seetreet.bean.content.ContentBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.dao.MongoRecDAO;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class LikeController
 */
@WebServlet("/user/content/user/like/*")
public class LikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final String PREFIX = "/user/content/user/like/";
	
	private final String SEARCH = "/user/content/user/like/search/";
	private final String UPDATE = "/user/content/user/like/update/";
	private final String CHECK  = "/user/content/user/like/check/";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeController() {
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
		
		PrintWriter out = res.getWriter();
		
		String token = req.getHeader(UserBean.KEY_TOKEN);
		String contentId = req.getParameter(ContentBean.KEY_ID);
		
		try {
			if(cmd.contains(SEARCH)) {				
				out.print(ResBodyFactory.create(true,ResBodyFactory.STATE_GOOD_WITH_DATA, searchLikeByContentId(contentId)));
			} else if(cmd.contains(CHECK)) {
				out.print(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, MongoRecDAO.hasLove(token, contentId)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				out.print(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_DB, null));
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
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
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String cmd = reqURI.substring(contextPath.length());
		PrintWriter out = res.getWriter();
		String token = req.getHeader(UserBean.KEY_TOKEN);
		String contentId = req.getParameter(ContentBean.KEY_ID);
		try {
			if(cmd.contains(UPDATE)) {
				boolean isLike = Boolean.parseBoolean(req.getParameter("islike"));
				boolean success = updateLikeByContentId(token, contentId, isLike);
				
				out.print(ResBodyFactory.create(true, success?ResBodyFactory.STATE_GOOD_WITH_DATA : ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, success));
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				out.print(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_DB, null));
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if(out!=null) out.close();
		}
	}
	
	private int searchLikeByContentId(String contentId) {
		int count = MongoDAO.countLikesByContentId(contentId);
		System.out.println(count);
		return count;
	}
	
	private boolean updateLikeByContentId(String token , String contentId, boolean isLike) {
		return MongoDAO.updateLikesByContentId(token, contentId, isLike);
	}

}
