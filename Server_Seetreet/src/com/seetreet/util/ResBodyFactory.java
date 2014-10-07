package com.seetreet.util;

import java.util.Map;

import com.google.gson.JsonObject;

public class ResBodyFactory {
	public static final int STATE_GOOD_WITH_DATA = 0;
	public static final int STATE_FAIL_ABOUT_UNKNOWN_TOKEN = -1;
	public static final int STATE_FAIL_ABOUT_DB = -2;
	
	private static final String COMMENT_FAIL_ABOUT_UNKNOWN_TOKEN 	= "Please Check Your token.";
	private static final String COMMENT_FAIL_ABOUT_DB				= "Error On Server DB.";
	
	public static String create(boolean state , int key , JsonObject data) {
		JsonObject result = new JsonObject();
		result.addProperty("state", state);
		switch(key) {
		case STATE_GOOD_WITH_DATA :			
			result.add("data", data);
			break;
		case STATE_FAIL_ABOUT_DB :
			result.addProperty("data" , COMMENT_FAIL_ABOUT_DB);
			break;
		case STATE_FAIL_ABOUT_UNKNOWN_TOKEN :
			result.addProperty("data" , COMMENT_FAIL_ABOUT_UNKNOWN_TOKEN);
			break;
		}		
		
		return result.toString();
	}
}
