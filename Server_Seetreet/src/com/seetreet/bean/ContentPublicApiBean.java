package com.seetreet.bean;

import org.json.JSONObject;

public class ContentPublicApiBean {
	private String contentId;
	private String contentTitle;
	private String contentGenre;
	private String contentType;
	private String contentStartTime;
	private String contentEndTime;
	private ArtistBean[] artist;
	private ProviderBean provider;
	private int likeCount;
	private String isConfirmed_artistId;
	private String confirmedTime;
	private boolean isFinished;
	
		
	public static final String KEY_CONTENTID 			= "contentId";
	public static final String KEY_CONTENTTITLE 		= "contentTitle";
	public static final String KEY_GENRE				= "contentGenre";
	public static final String KEY_TYPE					= "contentType";
	public static final String KEY_EVENTSTARTDATE	 	= "contentStartTime";
	public static final String KEY_EVENTENDDATE			= "contentEndTime";
	public static final String KEY_ARTIST				= "artists";
	public static final String KEY_PROVIDER				= "provider";
	public static final String KEY_MODIFIEDTIME			= "modifiedtime";
	public static final String KEY_OVERVIEW				= "overview";
	public static final String KEY_ISCONFIRMED_ARTISTID	= "isConfirmed_artistId";
	public static final String KEY_CONFIRMEDTIME		= "confirmedTime";
	public static final String KEY_FINISHEDTIME			= "isFinished";
	public static final String KEY_REPLY				= "reply";
	public static final String KEY_LIKECOUNT 			= "likeCount";
	
	

	public ContentPublicApiBean(ProviderBean _prov, JSONObject _existObject, JSONObject _detailObject){
		try{
			// reply 처리 안함.
			this.contentId = String.valueOf(_existObject.getLong("contentid"));
			this.contentTitle = _existObject.getString("title");
			this.contentGenre = _prov.getFavoriteGenre()[0].getDetailGenre();
			this.contentType = "PUBLIC";
			this.contentStartTime = String.valueOf(_existObject.getLong("eventstartdate") + "0000AM");
			this.contentEndTime = String.valueOf(_existObject.getLong("eventenddate") + "0000AM");
			this.contentType = "PUBLIC";
			this.artist = null;
			this.provider = _prov;
			this.likeCount = 0;
			this.isConfirmed_artistId = "PUBLIC";
			this.confirmedTime = this.contentStartTime;
			this.isFinished = false;

		}catch(Exception e){
			e.printStackTrace();
		}
		
 
	}
	
	
	public static String getCategoryTocontentGenre(String _category){
		String result;

		if(_category.equals("A02070100"))
			result = "문화관광축제";
		else if(_category.equals("A02070200"))
			result = "일반축제";
		else if(_category.equals("A02080100"))
			result = "전통공연";
		else if(_category.equals("A02080200"))
			result = "연극";
		else if(_category.equals("A02080300"))
			result = "뮤지컬";
		else if(_category.equals("A02080400"))
			result = "오페라";
		else if(_category.equals("A02080500"))
			result = "전시회";
		else if(_category.equals("A02080600"))
			result = "박람회";
		else if(_category.equals("A02080700"))
			result = "컨벤션";
		else if(_category.equals("A02080800"))
			result = "무용";
		else if(_category.equals("A02080900"))
			result = "클래식음악회";
		else if(_category.equals("A02080100"))
			result = "대중콘서트";
		else if(_category.equals("A02081100"))
			result = "영화";
		else if(_category.equals("A02081200"))
			result = "스포츠경기";
		else if(_category.equals("A02081300"))
			result = "기타행사";
		else 
			result = "null";
		
		return result; 
	}
	
	/*	
	public String getModifiedTime(){
		return modifiedTime;
	}
	*/
	public boolean getIsFinished(){
		return isFinished;
	}
	public String getConfirmedTime(){
		return confirmedTime;
	}
	public String getConfirmed_artistId(){
		return isConfirmed_artistId;
	}
	public ProviderBean getProvider(){
		return provider;
	}
	public ArtistBean[] getArtist(){
		return artist;
	}
	public String getContentGenre(){
		return contentGenre;
	}
	
	public String getContentType(){
		return contentType;
	}
	public String getContentName(){
		return contentTitle;
	}
	public String getContentId(){
		return contentId;
	}
	
	public String getEventStartDate(){
		return contentStartTime;
	}
	public int getLikeCount(){
		return likeCount;
	}
	public String getEventEndDate(){
		return contentEndTime;
	}
	
}
