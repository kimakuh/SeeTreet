package com.seetreet.http;


import java.io.IOException;

import com.seetreet.util.C;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpControl {
	public static void getContentsId(){
		System.out.println("HTTPCONSTENT ID");
		
		HttpClient httpclient = HttpInstance.INSTANCE.getHttp();
		//HttpClient httpclient = new DefaultHttpClient();
		String url = C.APISERVER + "/detailCommon?"+C.SERVICEKEY+"&contentId=971122&defaultYN=Y&overviewYN=Y&mapinfoYN=Y&MobileOS=ETC&MobileApp=AppTesting&_type=json";
		System.out.println(url);
		HttpGet httpget = new HttpGet(url);
		try {
			//HttpResponse response = httpclient.execute(httpget);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpget, responseHandler);
			
			System.out.println(responseBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return true;
	}
}
