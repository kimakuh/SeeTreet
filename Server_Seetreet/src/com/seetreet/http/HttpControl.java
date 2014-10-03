package com.seetreet.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jdk.nashorn.internal.parser.JSONParser;

import com.seetreet.bean.ApiContentIdListBean;
import com.seetreet.util.C;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpControl {
	public static void getContentsId() throws JSONException {
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		//HttpClient httpclient = new DefaultHttpClient();
		String url = C.APISERVER + "/searchFestival?"+C.SERVICEKEY+"&eventStartDate=20141001&listYN=Y&numOfRows=1000&MobileOS=ETC&MobileApp=AppTesting&_type=json";
		System.out.println(url);
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpget);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
			StringBuffer result = new StringBuffer();
			String line = "";
			while((line=rd.readLine())!= null){
				result.append(line);
			}
			//ResponseHandler<String> responseHandler = new BasicResponseHandler();
			//String responseBody = httpclient.execute(httpget, responseHandler);
			//String res = new String(responseBody.getBytes("EUC-KR"));
			//String resultString = result.toString();
			
			JSONObject obj = new JSONObject(result.toString());
			//System.out.println(result);
			System.out.println(obj);
			JSONArray jArray = obj.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
				
			int size = jArray.length();
			System.out.println("JSON SIZE : "+size);
			
			for(int i = 0;i<size;i++){
				ApiContentIdListBean temp = new ApiContentIdListBean(jArray.getJSONObject(i).getString("title"),jArray.getJSONObject(i).getInt("contentid"), jArray.getJSONObject(i).getInt("eventstartdate"), jArray.getJSONObject(i).getInt("eventenddate"));
				System.out.println(temp.getContentName());
				System.out.println(temp.getContentId());
				System.out.println(temp.getEventStartDate());
				System.out.println(temp.getEventEndDate());
				System.out.println("===========================");
			}
			//return obj;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		
		//return true;
	}
}
