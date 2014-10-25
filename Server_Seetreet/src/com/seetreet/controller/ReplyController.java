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
			String contentId = cmd.replace(SEARCH+"/", "");
			String token = req.getHeader(UserBean.KEY_TOKEN);
			String email = req.getParameter(UserBean.KEY_EMAIL);
			String text  = req.getParameter(ReplyBean.KEY_REPLYTEXT);
			String image = req.getParameter(ReplyBean.KEY_REPLYIMAGE);
			
			PrintWriter out = res.getWriter();
			
			try {
				if(MongoDAO.isUser(email, token)) {
					ReplyBean[] list = MongoDAO.getReplyByContentId(contentId);					
					JSONArray arr = new JSONArray();					
					for(ReplyBean bean : list) {
						arr.put(bean.getJson());
					}
					out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, arr));
				}else {
					out.write(ResBodyFactory.create(false, ResBodyFactory.STATE_FAIL_ABOUT_UNKNOWN_TOKEN, new JsonObject()));
				}
			} catch (Exception e) {
				// TODO: handle exception
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
		
		
		if(cmd.contains(ENROLL)) {
			System.out.println(">> enroll");
		}else if(cmd.contains(UPDATE)) {
			System.out.println(">> update");
		}
	}
	
	
}
