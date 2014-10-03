package com.seetreet.http;


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


 
public enum HttpInstance {
	INSTANCE;
	
	private HttpClient client;
	
	HttpInstance() {
		System.out.println("httpInstance-----------------");
		//	client = new DefaultHttpClient();
			client = new DefaultHttpClient();
			//System.out.println("httpInstance-----------------");
	}
	
	public HttpClient getHttp(){
		return client;
	}
}
