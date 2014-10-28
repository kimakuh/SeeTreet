package com.seetreet.http;

import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.seetreet.bean.ContentPublicApiBean;
import com.seetreet.bean.LocationBean;
import com.seetreet.bean.ProviderBean;
import com.seetreet.dao.MongoDAO;
import com.seetreet.util.C;

public  class HttpCall {
	
	public static JSONArray getContentIds() throws ClientProtocolException, IOException{
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		//HttpClient httpclient = new DefaultHttpClient();
		String url = C.APISERVER + C.SEARCHFESTIVAL+ C.SERVICEKEY+ C.CONTENTIDDATELIST +C.SETTINGVALUE;
		//System.out.println(url);
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
	
	public static JSONObject getContentObject(JSONObject notExistContent){
		//ApiContentBean contentInfo = null;
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		String url = null;
		try{
			
			url = C.APISERVER + C.DETAILCOMMON+ C.SERVICEKEY+ C.CONTENTID +notExistContent.getInt("contentid")
					+C.DEFAULTYN + C.FIRSTIMAGEYN + C.MAPINFOYN + C.CATCODEYN + C.OVERVIEWYN + C.SETTINGVALUE;
			//System.out.println(url);
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
			StringBuffer result = new StringBuffer();
			String line = "";
			while((line=rd.readLine())!= null){
				result.append(line);
			}			
			
			
			JSONObject temp = new JSONObject(result.toString());
			JSONObject obj = temp.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
			/*
			ProviderBean pub = new ProviderBean(obj);
			MongoDAO.insertPublicProvider(pub);
			contentInfo = new ApiContentBean(notExistContent.getString("title"), notExistContent.getLong("contentid"), notExistContent.getLong("eventstartdate"),
					notExistContent.getLong("eventenddate"),"public", "public", "NULL", "공공ID", obj.getString("overview"), obj.getLong("modifiedtime"),
					notExistContent.getLong("eventstartdate"), notExistContent.getLong("eventenddate"), obj.getString("cat3"));
			*/
			return obj;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(url);
			return null;
		}
	}
	
	/*
	public static ProviderBean getProvider(JSONObject notExistContent){
		ApiContentBean contentInfo = null;
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		
		try{
			
			String url = C.APISERVER + C.DETAILCOMMON+ C.SERVICEKEY+ C.CONTENTID +notExistContent.getInt("contentid")
					+C.DEFAULTYN + C.FIRSTIMAGEYN + C.MAPINFOYN + C.CATCODEYN + C.OVERVIEWYN + C.SETTINGVALUE;
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
			JSONObject obj = temp.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
			
			ProviderBean pub = new ProviderBean(obj);
			MongoDAO.insertPublicProvider(pub);
			contentInfo = new ApiContentBean(notExistContent.getString("title"), notExistContent.getLong("contentid"), notExistContent.getLong("eventstartdate"),
					notExistContent.getLong("eventenddate"),"public", "public", "NULL", "공공ID", obj.getString("overview"), obj.getLong("modifiedtime"),
					notExistContent.getLong("eventstartdate"), notExistContent.getLong("eventenddate"), obj.getString("cat3"));
			
			return contentInfo;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	*/
	/*
	public static ProviderBean createApiProvider(JSONObject obj){

		try {
			if(obj.has("mapx") || obj.has("mapy")){
				double[] temp={obj.getDouble("mapx"), obj.getDouble("mapy")};
				LocationBean loc = new LocationBean(obj.getString("title"), obj.getString("overview"), temp);
				String[] imageTemp = {"Null", "Null"};
				ProviderBean result = new ProviderBean("public", imageTemp, loc, "public", obj.getString("title"), "public", obj.getString("overview"), obj.getString("modifiedtime"));
				return result;
			}
			
			return null;
			
			}
		catch (JSONException e){// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	*/
}
