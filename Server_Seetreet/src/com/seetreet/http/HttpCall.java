package com.seetreet.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.seetreet.bean.ApiContentIdListBean;
import com.seetreet.util.C;

public  class HttpCall {
	
	public static JSONArray getContentIds() throws ClientProtocolException, IOException{
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		//HttpClient httpclient = new DefaultHttpClient();
		String url = C.APISERVER + C.SEARCHFESTIVAL+ C.SERVICEKEY+ C.CONTENTIDDATELIST + C.SETTINGVALUE;
		System.out.println(url);
		HttpGet httpget = new HttpGet(url);	
		HttpResponse response = httpclient.execute(httpget);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
		StringBuffer result = new StringBuffer();
		String line = "";
		while((line=rd.readLine())!= null){
			result.append(line);
		}
		JSONObject obj;
		JSONArray jArray;
		try {
			obj = new JSONObject(result.toString());
			jArray = obj.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
			return jArray;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static ApiContentIdListBean getContent(JSONObject notExistContent){
		ApiContentIdListBean contentInfo = null;
		//contentInfo.KEY_CONTENTTITLE 	= notExistContent.getString("title");
		//contentInfo.KEY_CONTENTID		= notExistContent.getInt("contentid");
				
				//tempContentId = jArray.getJSONObject(i).getInt("contentid");
				//tempTitle = jArray.getJSONObject(i).getString("title");
				//tempEventstartdate = jArray.getJSONObject(i).getInt("eventstartdate");
				//tempEventEndDate = jArray.getJSONObject(i).getInt("eventenddate");		
				
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		//HttpClient httpclient = new DefaultHttpClient();
		
		try{
			String url = C.APISERVER + C.DETAILCOMMON+ C.SERVICEKEY+ C.CONTENTID +notExistContent.getInt("contentid")+C.DEFAULTYN+ C.OVERVIEWYN + C.SETTINGVALUE;
			System.out.println(url);
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
			StringBuffer result = new StringBuffer();
			String line = "";
			while((line=rd.readLine())!= null){
				result.append(line);
			}
			JSONObject temp = new JSONObject(result.toString());
			System.out.println(result.toString());
			JSONObject obj;
			obj = temp.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
			contentInfo = new ApiContentIdListBean(notExistContent.getString("title"), notExistContent.getInt("contentid"), notExistContent.getInt("eventstartdate"), notExistContent.getInt("eventenddate")
					,"public", "public", "NULL", "NULL", "공공ID", obj.getString("overview"), obj.getInt("modifiedtime"), notExistContent.getInt("eventstartdate"), notExistContent.getInt("eventenddate"));
			return contentInfo;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
				
		
		
		
	}
	
}
