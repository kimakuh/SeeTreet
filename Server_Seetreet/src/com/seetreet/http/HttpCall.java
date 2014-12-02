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
//			e.printStackTrace();
			return null;
		}
	}
	
	public static JSONObject getContentObject(JSONObject notExistContent){
		//ApiContentBean contentInfo = null;
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		
		try{
			
			String url = C.APISERVER + C.DETAILCOMMON+ C.SERVICEKEY+ C.CONTENTID +notExistContent.getInt("contentid")
					+C.DEFAULTYN + C.FIRSTIMAGEYN + C.MAPINFOYN + C.CATCODEYN + C.ADDRESSYN + C.OVERVIEWYN + C.SETTINGVALUE;
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
			
			return obj;
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
	
	public static JSONArray getAddToCoord(String address){
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		JSONArray res = null;
		try{
			String url = C.DAUMADDTOCOORD + C.DAUMKEY + "&q=" + address.replaceAll("\\p{Space}", "") + "&output=json";
		
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
			StringBuffer result = new StringBuffer();
			String line = "";
			while((line=rd.readLine())!= null){
				result.append(line);
			}			
			
			JSONObject temp = new JSONObject(result.toString());
			res = temp.getJSONObject("channel").getJSONArray("item");
			System.out.println(res);
			
		}catch(Exception e){
//			e.printStackTrace();
		}
		return res;
		
	}
	
	public static JSONObject getCoordToAdd(String longitude, String latitude){
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		JSONObject res = null;
		try{
			String url = C.DAUMCOORDTOADD + C.DAUMKEY + "&longitude=" + longitude
					+ "&latitude=" + latitude + "&format=simple&output=json&inputCoordSystem=WGS84";
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
			StringBuffer result = new StringBuffer();
			String line = "";
			while((line=rd.readLine())!= null){
				result.append(line);
			}			
			res = new JSONObject(result.toString());
			System.out.println(res);
		}catch(Exception e){
//			e.printStackTrace();
		}
		return res;
	}


}
