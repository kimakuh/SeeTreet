package com.seetreet.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface BeanJson {
	public JSONObject getJson() throws JSONException;
}
