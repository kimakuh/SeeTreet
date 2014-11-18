package com.seetreet.recommand;


public enum RecommendEnum {
	LOVE_NAME("name") , 
	LOVE_VALUE("value") , 
	REC_HISTORY_USERID("user_id") , 
	REC_HISTORY_PROPERTIES("properties") , 
	SIM_MAP_SOURCE("source") , 
	SIM_MAP_TARGET("target") ,
	SIM_MAP_VALUE("value") ,
	REC_USERID("user_id") , 
	REC_PROPERTIES("properties") ;
	
	private String value;
	private RecommendEnum(String value) {
		this.value = value;
	}
	
	public String val() {
		return this.value;
	}
}
