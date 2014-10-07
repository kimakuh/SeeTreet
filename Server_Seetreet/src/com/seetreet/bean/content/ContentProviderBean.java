package com.seetreet.bean.content;

import org.json.JSONException;
import org.json.JSONObject;

import com.seetreet.bean.GenreBean;

public class ContentProviderBean {
	public static final String KEY_ID 		= "_id";
	public static final String KEY_TITLE 	= "content_title";
	public static final String KEY_GENRE	= "content_genre";
	public static final String KEY_TYPE 	= "content_type";
	public static final String KEY_STARTTIME= "content_start";
	public static final String KEY_ENDTIME	= "content_end";
	public static final String KEY_PROVIDER = "content_provider";
	private String id = null;
	private String title = null;
	private GenreBean	genre = null;
	private int	type;
	private int startTime;
	private int endTime;
	private String providerId = null;
	/* description : 이거는 프로바이더가 처음 콘텐츠를 처음 만들었을 때
	 * */
	public ContentProviderBean(String title , GenreBean genre , int type, int start, int end ,
			String providerId) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.genre = genre;
		this.type = type;
		this.startTime = start;
		this.endTime = end;
		this.providerId = providerId;		
	}
	
	public ContentProviderBean(String title , String genre , int type, int start, int end ,
			String providerId) throws JSONException {
		// TODO Auto-generated constructor stub
		JSONObject g = new JSONObject(genre);
		this.title = title;
		this.genre = new GenreBean(g.getString(GenreBean.KEY_CATEGORY), g.getString(GenreBean.KEY_DETAIL));
		this.type = type;
		this.startTime = start;
		this.endTime = end;
		this.providerId = providerId;		
	}
	
	public String getId() {
		return id;
	}
	
	public int getEndTime() {
		return endTime;
	}
	public GenreBean getGenre() {
		return genre;
	}
	public String getProviderId() {
		return providerId;
	}
	public int getStartTime() {
		return startTime;
	}
	public String getTitle() {
		return title;
	}
	public int getType() {
		return type;
	}
}
