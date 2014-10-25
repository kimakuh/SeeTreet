package com.seetreet.bean.content;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.seetreet.bean.ArtistBean;
import com.seetreet.bean.BeanJson;
import com.seetreet.bean.GenreBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.bean.ReplyBean;

public class ContentBean extends ContentArtistBean{	
	private String isConfirmed_artistId = null;
	private String confirmedTime = null;
	private boolean isFinished = false;
	private ReplyBean[] replies = null;
	
	
	
	
	public static final String KEY_C_ARTIST = "isConfirmed_artistId";
	public static final String KEY_C_TIME	= "ConfirmedTime";
	public static final String KEY_FINISHIED= "isFinished";
	public static final String KEY_REPLY 	= "reply";
	
	
	/* description : 유저가 볼 때
	 * */
	public ContentBean(String title , GenreBean genre , String type, int start, int end ,
			ProviderBean provider , ArtistBean[] artist , String confirmed_artist , String confirmed_time ,
			boolean isFinished , ReplyBean[] replies) {
		// TODO Auto-generated constructor stub
		super(title, genre, type, start, end, provider, artist);		
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
	public JSONObject getJson() {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray jsArtist = new JSONArray();
		try {			
			for(ArtistBean a : getArtist()) {
				jsArtist.put(a.getJson());
			}
			result.put(KEY_ID, getId());
			result.put(KEY_TITLE, getTitle());
			result.put(KEY_GENRE, getGenre().getJson());
			result.put(KEY_TYPE, getType());
			result.put(KEY_STARTTIME, getStartTime());
			result.put(KEY_ENDTIME, getEndTime());
			result.put(KEY_C_TIME, getConfirmedTime());
			result.put(KEY_FINISHIED, isFinished());
			result.put(KEY_PROVIDER, getProvider().getJson());
			result.put(KEY_ARTIST, jsArtist);
			result.put(KEY_C_ARTIST, getIsConfirmed_artistId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return result;
	}
	
}
