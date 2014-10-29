package com.seetreet.util;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class ResBodyFactory {
	public static final int STATE_GOOD_WITH_DATA = 0;
	public static final int STATE_FAIL_ABOUT_UNKNOWN_TOKEN = -1;
	public static final int STATE_FAIL_ABOUT_DB = -2;
	public static final int STATE_FAIL_ABOUT_WRONG_INPUT = -3;
	public static final int STATE_FAIL_ABOUT_EMPTY = -4;
	
	private static final String COMMENT_FAIL_ABOUT_UNKNOWN_TOKEN 	= "Please Check Your token.";
	private static final String COMMENT_FAIL_ABOUT_DB				= "Error On Server DB.";
	private static final String COMMENT_FAIL_ABOUT_WRONG_INPUT 		= "Please check your input.";
	private static final String COMMENT_FAIL_ABOUT_EMPTY			= "Empty list. please check page you want";
	

	public static String create(boolean state , int key , Object data) throws JSONException{
		try {
			JSONObject result = new JSONObject();			
			result.put("state", state);
			switch (key) {
			case STATE_GOOD_WITH_DATA:
				result.put("data", data);
				break;
			case STATE_FAIL_ABOUT_DB:
				result.put("data", COMMENT_FAIL_ABOUT_DB);
				break;
			case STATE_FAIL_ABOUT_UNKNOWN_TOKEN:
				result.put("data", COMMENT_FAIL_ABOUT_UNKNOWN_TOKEN);
				break;
			case STATE_FAIL_ABOUT_WRONG_INPUT :
				result.put("data" , COMMENT_FAIL_ABOUT_WRONG_INPUT);
				break;
			case STATE_FAIL_ABOUT_EMPTY :
				result.put("data" , COMMENT_FAIL_ABOUT_EMPTY);
				break;
			}

			return result.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		JSONObject json = new JSONObject();
		json.put("state", false);
		return json.toString();
	}
}
