package com.seetreet.bean;

public class ReplyBean {
	private String replyId;
	private String contentId;
	private float replyscore;
	private String replytext;
	private String replyimage;
	
	public ReplyBean(String replyId, String contentId, float replyscore, String replytext, String replyimage) {
		// TODO Auto-generated constructor stub
		
		this.contentId = contentId;
		this.replyId = replyId;
		this.replyscore = replyscore;
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
	public float getReplyscore() {
		return replyscore;
	}
	public String getReplytext() {
		return replytext;
	}
}
