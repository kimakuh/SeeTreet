package com.seetreet.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GenreBean implements BeanJson{
	private String category;
	private String detailGenre;
	
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_DETAIL 	= "detailGenre";
	
	public GenreBean(String category, String detailGenre) {
		// TODO Auto-generated constructor stub
		this.category = category;
		this.detailGenre = detailGenre;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDetailGenre() {
		return detailGenre;
	}

	@Override
	public JSONObject getJson() throws JSONException {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put(KEY_CATEGORY, getCategory());
		result.put(KEY_DETAIL, getDetailGenre());
		
		return result;
	}
}
