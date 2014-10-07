package com.seetreet.bean.content;

import com.google.gson.JsonObject;
import com.seetreet.bean.BeanJson;
import com.seetreet.bean.GenreBean;
import com.seetreet.bean.ReplyBean;

public class ContentBean extends ContentArtistBean implements BeanJson{	
	private String isConfirmed_artistId = null;
	private String confirmedTime = null;
	private boolean isFinished = false;
	private ReplyBean[] replies = null;
	
	
	
	
	public static final String KEY_C_ARTIST = "content_confirmed_artist";
	public static final String KEY_C_TIME	= "content_confirmed_time";
	public static final String KEY_FINISHIED= "content_isfinished";
	public static final String KEY_REPLY 	= "content_reply";
	
	
	/* description : 유저가 볼 때
	 * */
	public ContentBean(String title , GenreBean genre , int type, int start, int end ,
			String providerId , String artistId , String confirmed_artist , String confirmed_time ,
			boolean isFinished , ReplyBean[] replies) {
		// TODO Auto-generated constructor stub
		super(title, genre, type, start, end, providerId, artistId);		
		this.confirmedTime = confirmed_time;
		this.isConfirmed_artistId = confirmed_artist;
		this.isFinished = isFinished;
		this.replies = replies;
	}
	
	public String getConfirmedTime() {
		return confirmedTime;
	}
	public String getIsConfirmed_artistId() {
		return isConfirmed_artistId;
	}
	public boolean isFinished() {
		return isFinished;
	}
	public ReplyBean[] getReplies() {
		return replies;
	}
	
	@Override
	public JsonObject getJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
