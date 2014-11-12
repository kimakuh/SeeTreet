package com.seetreet.bean.content;

import org.json.JSONException;
import org.json.JSONObject;

import com.seetreet.bean.BeanJson;
import com.seetreet.bean.GenreBean;
import com.seetreet.bean.ProviderBean;

public class ContentProviderBean implements BeanJson{
	public static final String KEY_ID 		= "_id";
	public static final String KEY_TITLE 	= "contentTitle";
	public static final String KEY_GENRE	= "contentGenre";
	public static final String KEY_TYPE 	= "contentType";
	public static final String KEY_STARTTIME= "contentStartTime";
	public static final String KEY_ENDTIME	= "contentEndTime";
	public static final String KEY_PROVIDER = "provider";
	private String id = null;
	private String title = null;
	private GenreBean	genre = null;
	private String	type;
	private String startTime;
	private String endTime;
	private ProviderBean provider = null;
	/* description : 이거는 프로바이더가 처음 콘텐츠를 처음 만들었을 때
	 * */
	public ContentProviderBean(String title , GenreBean genre , String type, String start, String end ,
			ProviderBean provider) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.genre = genre;
		this.type = type;
		this.startTime = start;
		this.endTime = end;
		this.provider = provider;		
	}
	
	public ContentProviderBean(String title , String genre , String type, String start, String end ,
			ProviderBean provider) throws JSONException {
		// TODO Auto-generated constructor stub
		JSONObject g = new JSONObject(genre);
		this.title = title;
		this.genre = new GenreBean(g.getString(GenreBean.KEY_CATEGORY), g.getString(GenreBean.KEY_DETAIL));
		this.type = type;
		this.startTime = start;
		this.endTime = end;
		this.provider = provider;		
	}
	
	public String getId() {
		return id;
	}
	
	public String getEndTime() {
		return endTime;
	}
	public GenreBean getGenre() {
		return genre;
	}
	public ProviderBean getProvider() {
		return provider;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getTitle() {
		return title;
	}
	public String getType() {
		return type;
	}

	@Override
	public JSONObject getJson() throws JSONException {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put(KEY_ID, getId())
			.put(KEY_TITLE, getTitle())
			.put(KEY_GENRE, getGenre().getJson())
			.put(KEY_STARTTIME, getStartTime())
			.put(KEY_TYPE, getType())
			.put(KEY_ENDTIME, getEndTime())
			.put(KEY_PROVIDER, getProvider().getJson());
		return null;
	}
}
