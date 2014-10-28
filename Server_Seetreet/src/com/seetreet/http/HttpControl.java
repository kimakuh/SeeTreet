package com.seetreet.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mongodb.BasicDBObject;
import com.seetreet.bean.ContentPublicApiBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.C;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpControl {
	public static void getContents() {
		try{
			JSONArray jArray;
			jArray = HttpCall.getContentIds();
			int size = jArray.length();
			//System.out.println("JSON SIZE : "+size);
			
			for(int i = 0;i<size;i++){
				JSONObject existObject = jArray.getJSONObject(i);
				// true이면 없는거, false면 기존에 있는거				
				boolean resultContent = MongoDAO.checkPublicApiContentId(existObject.getLong("contentid"));

				if(resultContent != false){
					// 새로 삽입
					JSONObject obj = HttpCall.getContentObject(existObject);
					
					ProviderBean prov = new ProviderBean(obj);
					BasicDBObject provObj = MongoDAO.insertPublicProvider(prov);
					
					ContentPublicApiBean contentInfoBean = new ContentPublicApiBean(prov, existObject, obj);
					MongoDAO.insertPublicApiContent(contentInfoBean, provObj);
					
				}
				else{
					
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
			
			//return obj;
		
		
		//return true;
	}
}
