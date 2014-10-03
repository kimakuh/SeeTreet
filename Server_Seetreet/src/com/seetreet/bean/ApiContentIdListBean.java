package com.seetreet.bean;

public class ApiContentIdListBean {
	private String contentName;
	private int contentId;
	private int eventStartDate;
	private int eventEndDate;
	
	public ApiContentIdListBean(String _contentName, int _contentId, int _eventStartDate, int _eventEndDate){
		this.contentName = _contentName;
		this.contentId = _contentId;
		this.eventStartDate = _eventStartDate;
		this.eventEndDate = _eventEndDate;
	}

	public String getContentName(){
		return contentName;
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
