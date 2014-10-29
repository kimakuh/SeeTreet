package com.seetreet.bean;

public interface UserLoginBean extends BeanJson {
	public static final String KEY_EMAIL 	= "user_email";	
	public static final String KEY_AGE		= "user_age";	
	public static final String KEY_NAME		= "user_name";
	public static final String KEY_TOKEN	= "_id";
	public String getToken();
	public String getName();
	public String getEmail();
	public int 	  getAge();
}
