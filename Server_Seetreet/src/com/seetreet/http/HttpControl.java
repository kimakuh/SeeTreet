package com.seetreet.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.seetreet.bean.ApiContentIdListBean;
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
				//System.out.println("JSONARRAY : " + i + " VALUE: " + existObject.getInt("contentid"));
				
				boolean resultContent = MongoDAO.checkPublicApiContentId(existObject.getInt("contentid"));
				//System.out.println("RESULT : "+resultContent);
				if(resultContent != false){
					// 새로 삽입
					//System.out.println("checkcheckID :" +  i);
					ApiContentIdListBean contentInfoBean = HttpCall.getContent(existObject);
					MongoDAO.insertPublicApiContent(contentInfoBean);
					
				}
				else{
					
				}
				/*
				System.out.println(temp.getContentName());
				System.out.println(temp.getContentId());
				System.out.println(temp.getEventStartDate());
				System.out.println(temp.getEventEndDate());
				System.out.println("===========================");
				MongoDAO.insertPublicApiContentId(temp);
				*/
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
			
			//return obj;
		
		
		//return true;
	}
}
