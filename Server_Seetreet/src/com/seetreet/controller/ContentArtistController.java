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

import com.seetreet.bean.ArtistBean;
import com.seetreet.bean.LocationBean;
import com.seetreet.bean.UserBean;
import com.seetreet.bean.content.ContentBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.ResBodyFactory;

/**
 * Servlet implementation class ContentArtistController
 */
@WebServlet("/user/content/artist/*")
public class ContentArtistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String PREFIX_SEARCH = "/user/content/artist/search/";
	private static final String PREFIX_APPLY = "/user/content/artist/apply/";
	private static final String PREFIX_DELETE = "/user/content/artist/delete/";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentArtistController() {
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
		try {
			if(cmd.contains(PREFIX_SEARCH)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, searchContentByArtist(req, res)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(out != null) out.close();
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
			if(cmd.contains(PREFIX_APPLY)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, searchContentByArtist(req, res)));
			} else if(cmd.contains(PREFIX_DELETE)) {
				out.write(ResBodyFactory.create(true, ResBodyFactory.STATE_GOOD_WITH_DATA, searchContentByArtist(req, res)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(out != null) out.close();
		}
	}

	private JSONArray searchContentByArtist(HttpServletRequest req, HttpServletResponse res) {
		JSONArray arr = new JSONArray();
		try {
			JSONObject artist = MongoDAO.getArtist(
					(String) req.getAttribute(UserBean.KEY_EMAIL),
					req.getParameter(UserBean.KEY_TOKEN));
			JSONObject location = artist.getJSONArray(ArtistBean.KEY_LOCATIONS)
					.getJSONObject(0);
			JSONArray coords = location
					.getJSONArray(LocationBean.KEY_COORDINATE);

			int page = Integer.parseInt(req.getParameter("page"));
			ContentBean[] beans = MongoDAO.searchContentByLocationFromArtist(
					coords.getDouble(0), coords.getDouble(1), page);
			
			for (ContentBean bean : beans) {
				arr.put(bean.getJson());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return arr;
	}
	
	/* 내일 할 것
	 * 아티스트가 찾기 확인
	 * 아트리스가 삭제하거나 지원하는경우
	 * 프로바이더 등록
	 * 프로바이더 수정, 삭제
	 * */
	private JSONObject deleteContentByArtist(HttpServletRequest req, HttpServletResponse res) {
		return null;
	}
	private JSONObject applyContentByArtist(HttpServletRequest req, HttpServletResponse res) {
		return null;
	}
	
}
