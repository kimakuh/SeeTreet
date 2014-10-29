package com.seetreet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DefaultEditorKit.InsertContentAction;

import org.json.JSONObject;

import com.mongodb.DBObject;
import com.seetreet.bean.GenreBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.content.ContentProviderBean;
import com.seetreet.dao.MongoDAO;

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
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentProviderController() {
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
		
		System.out.println("> SERVLET : " + PREFIX);
		
		if(cmd.contains(ENROLL)) {
			System.out.println(">> enroll");
		}else if(cmd.contains(SEARCH)) {
			System.out.println(">> search");
		}else if(cmd.contains(UPDATE)) {
			System.out.println(">> update");
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String cmd = reqURI.substring(contextPath.length());
		
		System.out.println("> SERVLET : " + PREFIX);
		
		if(cmd.contains(ENROLL)) {
			System.out.println(">> enroll Post");
			enrollContentByProvider(req, res);
		}else if(cmd.contains(SEARCH)) {
			System.out.println(">> search Post");
		}else if(cmd.contains(UPDATE)) {
			System.out.println(">> update Post");
		}
		
	}
	
	private void enrollContentByProvider(HttpServletRequest req , HttpServletResponse res) throws IOException {
		//System.out.println((String)req.getParameter(UserBean.KEY_EMAIL));
		PrintWriter out = res.getWriter();
		JSONObject json = new JSONObject();
		
			String contentTitle = (String)req.getParameter(ContentProviderBean.KEY_TYPE);
			String contentStartTime = (String)req.getParameter(ContentProviderBean.KEY_STARTTIME);
			String contentEndTime = (String)req.getParameter(ContentProviderBean.KEY_ENDTIME);
			String tempPId = (String)req.getHeader(UserBean.KEY_TOKEN);
			//ProviderBean provider = MongoDAO.checkProviderId(tempPId);
			DBObject providerObject = MongoDAO.checkProviderId(tempPId);
		try{
			if(true != MongoDAO.insertContentByProvider(contentTitle, contentStartTime, contentEndTime, tempPId, providerObject)){
				System.out.println("Fail to Database");
				//Send to Client to Fail Return ~~~~~
				json.put("state", false);
				out.write(json.toString());
			}			
			else{
				//Send to Client RETURN~~~~~~~~
				json.put("state", true);
				out.write(json.toString());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	
	}
}
