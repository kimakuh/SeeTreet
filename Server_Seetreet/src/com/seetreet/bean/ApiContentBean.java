package com.seetreet.bean;

public class ApiContentBean {
	private String contentTitle;
	private String contentGenre;
	private String contentType;
	private String artist;
	private String provider;
	private String isConfirmed_artistId;
	private String overview;
	private String confirmedTime;
	private String isFinishedTime;
	private String contentId;
	private String eventStartDate;
	private String eventEndDate;
	private String modifiedTime;
	
	
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
	
	public ApiContentBean(String _contentTitle, String _contentId, String _eventStartDate, String _eventEndDate, String _contentType, String _artist,
			String _provider, String _isConfirmed_artistId, String _overview, String _modifiedTime, String _confirmedTime, String _isFinishedTime, String _category){
		this.isFinishedTime = _isFinishedTime;
		this.confirmedTime = _confirmedTime;
		this.isConfirmed_artistId = _isConfirmed_artistId;
		this.contentType = _contentType;
		this.artist = _artist;
		this.provider = _provider;
		this.overview = _overview;
		this.modifiedTime = _modifiedTime;
		this.contentTitle = _contentTitle;
		this.contentGenre = getCategoryTocontentGenre(_category);
		this.contentId = _contentId;
		this.eventStartDate = _eventStartDate;
		this.eventEndDate = _eventEndDate;
	}
	
	public String getCategoryTocontentGenre(String _category){
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
	
	public String getOverview(){
		return overview;
	}
	
	public String getModifiedTime(){
		return modifiedTime;
	}
	
	public String getIsFinishedTime(){
		return isFinishedTime;
	}
	public String getConfirmedTime(){
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
	public String getContentId(){
		return contentId;
	}
	
	public String getEventStartDate(){
		return eventStartDate;
	}
	
	public String getEventEndDate(){
		return eventEndDate;
	}
}
