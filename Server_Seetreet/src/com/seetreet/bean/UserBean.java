package com.seetreet.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class UserBean implements UserLoginBean{
	private String email;
	private String pw;
	private GenreBean[] favoriteGenre;
	private String description;
	private int age;
	private String name;
	private String phone;
	private ReplyBean[] replies;
	private ProviderBean provider;
	private ArtistBean artist;
	private String modTime;
	private String token;
	
	public static final String KEY_EMAIL 	= "user_email";
	public static final String KEY_PW		= "user_pw";
	public static final String KEY_AGE		= "user_age";
	public static final String KEY_PHONE	= "user_phone";
	public static final String KEY_NAME		= "user_name";
	public static final String KEY_TOKEN	= "_id";
	public static final String KEY_MODTIME 	= "user_modtime";
	public static final String KEY_PROVIDER = "user_provider";
	public static final String KEY_ARTIST 	= "user_artist";
	public static final String KEY_REPLY 	= "user_reply";
	public static final String KEY_GENRE 	= "user_genre";
	public static final String KEY_DESCRIPT	= "user_descript";
//	public static final String KEY_
//	public static final String KEY_
//	public static final String KEY_
//	public static final String KEY_
	
	
	
	public UserBean(String email, String name, int age , String phone , String pw){
		this.email = email;
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.pw = pw;
	}
	
	
	public UserBean(String email, String name, int age, String phone
			, String modTime ,String token) {
		this.email = email;
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.modTime = modTime;
		this.token = token;
	}
	
	public UserBean(String email, String name, int age, String phone, String pw,
			String modTime, GenreBean[] favoriteGenre, ReplyBean[] replies, String token, 
			ProviderBean provider , ArtistBean artist , String description){
		this.email 			= email 	== null ? null : email;
		this.name 			= name 		== null ? null : name;
		this.age 			= age 		< 0 ? -1 : age;
		this.phone 			= phone 	== null ? null : phone;
		this.modTime 		= modTime 	== null ? null : modTime;
		this.replies 		= replies 	== null ? null : replies;
		this.favoriteGenre 	= favoriteGenre == null ? null : favoriteGenre;
		this.provider 		= provider 	== null ? null : provider;
		this.artist 		= artist 	== null ? null : artist;
		this.description 	= description 	== null ? null : description;
		this.token			= token		== null ? null : token;
	}
	
	public int getAge() {
		return age;
	}
	public ArtistBean getArtist() {
		return artist;
	}
	public String getDescription() {
		return description;
	}
	public GenreBean[] getFavoriteGenre() {
		return favoriteGenre;
	}
	public String getModTime() {
		return modTime;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public ProviderBean getProvider() {
		return provider;
	}
	public ReplyBean[] getReplies() {
		return replies;
	}
	
	public String getEmail() {
		return email;
	}
	public String getPw() {
		return pw;
	}
	public String getToken() {
		return token;
	}


	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject temp = new JSONObject();
		temp.put(KEY_EMAIL, email);
		temp.put(KEY_NAME, name);
		temp.put(KEY_TOKEN, token);
		temp.put(KEY_AGE, age);
		return temp;
	}
}
