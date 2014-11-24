package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.seetreet.bean.ReplyBean;
import com.seetreet.bean.UserBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class ReplyController
 */
@WebServlet("/user/content/user/reply/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String PREFIX = "/user/content/user/reply"; 
	private final String ENROLL = "/user/content/user/reply/enroll";
	private final String SEARCH = "/user/content/user/reply/search";
    private final String UPDATE = "/user/content/user/reply/update";
    private final String DELETE = "/user/content/user/reply/delete";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyController() {
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
			PrintWriter out = res.getWriter();
			
			try {
				JSONArray arr = searchReply(req, res);
				if(arr.length()== 0) {
					out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_EMPTY, null));
					return;
				}
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, arr));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if(out!= null) try{out.close();}catch(Exception e) {e.printStackTrace();}
			}			
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
		
		try {
			if(cmd.contains(ENROLL)) {				
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, enrollReply(req, res)));
			}else if(cmd.contains(UPDATE)) {
				System.out.println(">> update");
			}else if(cmd.contains(DELETE)) {
				boolean isDeleted = deleteReply(req, res);
				if(isDeleted) {
					out.write(ResBodyFactory.create(isDeleted, ResBodyFactory.STATE_GOOD_WITH_DATA, null));
				}else {
					out.write(ResBodyFactory.create(isDeleted, ResBodyFactory.STATE_FAIL_ABOUT_WRONG_INPUT, null));
				}				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(out != null) out.close();
		}
		
	}
	
	private JSONArray searchReply(HttpServletRequest req, HttpServletResponse res) {
		JSONArray arr = new JSONArray();
		try{
			String contentId = (String)req.getParameter(ReplyBean.KEY_CONTENTID);
			int    page  = Integer.parseInt((String)req.getParameter("page"));
			ReplyBean[] list = MongoDAO.getReplyByContentId(contentId, page);			
								
			for(ReplyBean bean : list) {
				arr.put(bean.getJson());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return arr;
	}
	
	private JSONObject enrollReply(HttpServletRequest req, HttpServletResponse res) {			
		String contentId = req.getParameter(ReplyBean.KEY_CONTENTID);
		String replytext = req.getParameter(ReplyBean.KEY_REPLYTEXT);
		String replyimage= req.getParameter(ReplyBean.KEY_REPLYIMAGE);	
		
		ReplyBean reply = new ReplyBean((String)req.getAttribute(UserBean.KEY_EMAIL) , contentId, replytext, replyimage);
		return MongoDAO.enrollReply(reply);					
	}
	
	private void updateReply(HttpServletRequest req, HttpServletResponse res) {
		
	}
	
	private boolean deleteReply(HttpServletRequest req, HttpServletResponse res) {
		String contentId 	= req.getParameter(ReplyBean.KEY_CONTENTID);
		String replyId 		= req.getParameter(ReplyBean.KEY_ID);
		String email		= (String)req.getAttribute(UserBean.KEY_EMAIL);
		
		ReplyBean bean = new ReplyBean(email, replyId, contentId, null, null);
		return MongoDAO.deleteReply(bean);		
	}
}
