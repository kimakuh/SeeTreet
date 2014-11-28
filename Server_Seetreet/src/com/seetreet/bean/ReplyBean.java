package com.seetreet.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class ReplyBean implements BeanJson {
	private String replyId;
	private String contentId;	
	private String replytext;
	private String replyimage;
	private String userEmail;
	
	public static final String KEY_ID = "_id";
	public static final String KEY_MODTIME = "modTime";
	public static final String KEY_CONTENTID = "contentId";
	public static final String KEY_REPLYTEXT = "replytext";
	public static final String KEY_REPLYIMAGE = "replyimage";
	public static final String KEY_USEREMAIL = "userEmail";
	
	public ReplyBean(String email ,String contentId, String replytext, String replyimage) {
		// TODO Auto-generated constructor stub
		this.userEmail = email;
		this.contentId = contentId;
		this.replytext = replytext;
		this.replyimage = replyimage;
	}
	
	public ReplyBean(
			String email , 
			String replyId,
			String contentId, 
			String replytext, 
			String replyimage) {
		// TODO Auto-generated constructor stub
		this.userEmail = email;
		this.contentId = contentId;
		this.replyId = replyId;
		this.replytext = replytext;
		this.replyimage = replyimage;
	}
	
	public String getContentId() {
		return contentId;
	}
	public String getReplyId() {
		return replyId;
	}
	public String getReplyimage() {
		return replyimage;
	}
	public String getReplytext() {
		return replytext;
	}
	public String getUserEmail() {
		return userEmail;
	}

	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		obj.put(KEY_CONTENTID, this.contentId);
		obj.put(KEY_ID, this.replyId);
		obj.put(KEY_REPLYTEXT, this.replytext);
		obj.put(KEY_REPLYIMAGE, this.replyimage);
		obj.put(KEY_USEREMAIL, this.userEmail);
		return obj;
	}
}
