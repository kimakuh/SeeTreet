package com.seetreet.bean;

public class ApiContentIdListBean {
	private String contentTitle;
	private String contentGenre;
	private String contentType;
	private String artist;
	private String provider;
	private String isConfirmed_artistId;
	private String overview;
	private int confirmedTime;
	private int isFinishedTime;
	private int contentId;
	private int eventStartDate;
	private int eventEndDate;
	private int modifiedTime;
	
	
	public static final String KEY_CONTENTID 			= "contentId";
	public static final String KEY_CONTENTTITLE 		= "contentTitle";
	public static final String KEY_GENRE				= "contentGenre";
	public static final String KEY_TYPE					= "contentType";
	public static final String KEY_EVENTSTARTDATE	 	= "contentStartTime";
	public static final String KEY_EVENTENDDATE			= "contentEndTime";
	public static final String KEY_ARTIST				= "artist";
	public static final String KEY_PROVIDER				= "provider";
	public static final String KEY_MODIFIEDTIME			= "modifiedtime";
	public static final String KEY_OVERVIEW				= "overview";
	public static final String KEY_ISCONFIRMED_ARTISTID	= "isConfirmed_artistId";
	public static final String KEY_CONFIRMEDTIME		= "confirmedTime";
	public static final String KEY_FINISHEDTIME			= "isFinished";
	public static final String KEY_REPLY				= "reply";
	
	public ApiContentIdListBean(String _contentTitle, int _contentId, int _eventStartDate, int _eventEndDate, String _contentGenre
			, String _contentType, String _artist, String _provider, String _isConfirmed_artistId, String _overview, int _modifiedTime, int _confirmedTime, int _isFinishedTime){
		this.isFinishedTime = _isFinishedTime;
		this.confirmedTime = _confirmedTime;
		this.isConfirmed_artistId = _isConfirmed_artistId;
		this.contentType = _contentType;
		this.artist = _artist;
		this.provider = _provider;
		this.overview = _overview;
		this.modifiedTime = _modifiedTime;
		this.contentTitle = _contentTitle;
		this.contentGenre = _contentGenre;
		this.contentId = _contentId;
		this.eventStartDate = _eventStartDate;
		this.eventEndDate = _eventEndDate;
	}
	public String getOverview(){
		return overview;
	}
	
	public int getModifiedTime(){
		return modifiedTime;
	}
	
	public int getIsFinishedTime(){
		return isFinishedTime;
	}
	public int getConfirmedTime(){
		return confirmedTime;
	}
	public String getConfirmed_artistId(){
		return isConfirmed_artistId;
	}
	public String getProvider(){
		return provider;
	}
	public String getArtist(){
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
	public int getContentId(){
		return contentId;
	}
	
	public int getEventStartDate(){
		return eventStartDate;
	}
	
	public int getEventEndDate(){
		return eventEndDate;
	}
}
