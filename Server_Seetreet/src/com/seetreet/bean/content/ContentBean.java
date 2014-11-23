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
	private String contentId;
	private String isConfirmed_artistId = null;
	private String confirmedTime = null;
	private boolean isFinished = false;
	private ReplyBean[] replies = null;
	private int 	likeCount;
	
	
	
	
	public static final String KEY_C_ARTIST = "isConfirmed_artistId";
	public static final String KEY_C_TIME	= "confirmedTime";
	public static final String KEY_FINISHIED= "isFinished";
	public static final String KEY_REPLY 	= "reply";
	public static final String KEY_CONTENTID= "_id";
	public static final String KEY_LIKECOUNT= "likecount";
	
	
	/* description : 유저가 볼 때
	 * */
	public ContentBean(String contentId, String title , GenreBean genre , String type, String start, String end ,
			ProviderBean provider , ArtistBean[] artist , String confirmed_artist , String confirmed_time ,
			boolean isFinished , ReplyBean[] replies,int likecount) {
		// TODO Auto-generated constructor stub
		super(contentId, title, genre, type, start, end, provider, artist);		
		this.confirmedTime = confirmed_time;
		this.isConfirmed_artistId = confirmed_artist;
		this.isFinished = isFinished;
		this.replies = replies;
		this.contentId = contentId;
		this.likeCount = likecount;
	}
	
	public String getContentId(){
		return contentId;
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
	
	public int getLikeCount() {
		return likeCount;
	}
	
	@Override
	public JSONObject getJson() {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray jsArtist = new JSONArray();
		try {
			if(getArtist() != null){
				for(ArtistBean a : getArtist()) {
					jsArtist.put(a.getJson());
				}	
			}
			else{
				jsArtist = null;
			}
			System.out.println("ContentBean GetJson : ");
			result.put(KEY_ID, getContentId());
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
			result.put(KEY_LIKECOUNT, getLikeCount());
			System.out.println("ContentBean GetJson END: ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return result;
	}
	
}
