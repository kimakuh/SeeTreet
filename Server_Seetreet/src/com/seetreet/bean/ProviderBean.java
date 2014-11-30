package com.seetreet.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.seetreet.bean.content.ContentBean;
import com.seetreet.http.HttpCall;

public class ProviderBean implements BeanJson{
	private String providerId;
	private String[] images;
	private String contentType;
	private GenreBean[] favoriteGenre;
	private LocationBean location;
	private String StoreTitle;
	private String StoreType;
	private String description;
	private ContentBean[] history;
	private String modTime;
	private String publicGenre;
	private String address;
	
	public static final String KEY_ID = "_id";
	public static final String KEY_ADDRESS = "StoreAddress";
	public static final String KEY_IMAGES = "providerImage";
	public static final String KEY_TYPE = "contentType";
	public static final String KEY_GENRE = "favoriteGenre";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_STORETITLE = "StoreTitle";
	public static final String KEY_STORETYPE = "StoreType";
	public static final String KEY_DESCRIPT = "description";
	public static final String KEY_MODTIME = "modifiedTime";
	
	
	public ProviderBean(
			String contentType, 
			String[] images ,
			LocationBean location, 
			GenreBean[] genre, 
			String storeTitle, 
			String storeType, 
			String description, 
			String address, 
			String modTime) {
		// TODO Auto-generated constructor stub
		this.contentType = contentType;
		this.images = images;
		this.location = location;
		this.StoreTitle = storeTitle;
		this.StoreType = storeType;
		this.description = description;
		this.favoriteGenre = genre;
		this.address = address;
		this.modTime = modTime;
	}
		
	public ProviderBean(
			String contentType, 
			String[] images ,
			LocationBean location, 
			String storeTitle, 
			String storeType, 
			String description,
			String providerId, 
			GenreBean[] genre, 
			ContentBean[] history, 
			String modTime, 
			String address) {
		// TODO Auto-generated constructor stub
		this.contentType = contentType;
		this.images = images;
		this.location = location;
		this.StoreTitle = storeTitle;
		this.StoreType = storeType;
		this.description = description;
		this.favoriteGenre = genre;
		this.history = history;
		this.modTime = modTime;
		this.address = address;
	}
	
	public ProviderBean(JSONObject obj){
		//怨듦났 API Bean 媛앹껜
		try{
			this.contentType = "PUBLIC";
			if(obj.has("firstimage")){
				if(obj.has("firstimage2")){
					String[] tempImage = {obj.getString("firstimage"),obj.getString("firstimage2")};
					this.images = tempImage;
				}
				else{
					String[] tempImage = {obj.getString("firstimage"), "null"};
					this.images = tempImage;
				}
			}else{
				this.images = null;
			}
				
			
			if(obj.has("telname") && obj.has("tel")){
				this.StoreTitle = obj.getString("telname") + "(" + obj.getString("tel") + ")";
			}else if(obj.has("telname")){
				this.StoreTitle = obj.getString("telname");
			}else
				this.StoreTitle = obj.getString("title");
			
			
			if(obj.has("overview")){
				this.description = obj.getString("overview");
			}
			if(obj.has("mapx") && obj.has("mapy")){
				LocationBean loc = new LocationBean(this.StoreTitle, this.description, obj.getDouble("mapx"), obj.getDouble("mapy"));
				this.location = loc;
			}
			if(obj.has("cat3")){
				this.StoreType = ContentPublicApiBean.getCategoryTocontentGenre(obj.getString("cat3"));
				GenreBean[] genre = {new GenreBean("공공", ContentPublicApiBean.getCategoryTocontentGenre(obj.getString("cat3")))};
				this.favoriteGenre = genre;
			}
			if(obj.has("modifiedtime")){
				this.modTime = String.valueOf(obj.getLong("modifiedtime"));
			}	
			if(obj.has("addr1")){
				this.address = obj.getString("addr1");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getContentType() {
		return contentType;
	}
	public String getDescription() {
		return description;
	}
	public GenreBean[] getFavoriteGenre() {
		return favoriteGenre;
	}
	public ContentBean[] getHistory() {
		return history;
	}
	public String[] getImages() {
		return images;
	}
	public LocationBean getLocation() {
		return location;
	}
	public String getModTime() {
		return modTime;
	}
	public String getProviderId() {
		return providerId;
	}
	public String getStoreTitle() {
		return StoreTitle;
	}
	public String getStoreType() {
		return StoreType;
	}
	public String getPublicGenre() {
		return publicGenre;
	}
	public String getAddress(){
		return address;
	}

	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray images = new JSONArray();
		JSONArray genres = new JSONArray();
		if(getImages() != null){
			for(String image : getImages()) {
				images.put(image);
			}
		}else
			images.put("");
		
		for(GenreBean genre : getFavoriteGenre()){
			genres.put(new JSONObject()
							.put(GenreBean.KEY_CATEGORY, genre.getCategory())
							.put(GenreBean.KEY_DETAIL, genre.getDetailGenre()));
		}
		
		JSONObject location = new JSONObject();
		location.put(LocationBean.KEY_NAME, getLocation().getName());
		location.put(LocationBean.KEY_DESCRIPT, getLocation().getDescription());
		location.put(LocationBean.KEY_COORDINATE, new JSONArray()
														.put(getLocation().getLatitude())
														.put(getLocation().getLongitude()));
		
		
		result.put(KEY_IMAGES, images);
		result.put(KEY_TYPE, getContentType());
		result.put(KEY_GENRE,genres);
		result.put(KEY_LOCATION, location);
		result.put(KEY_STORETITLE, getStoreTitle());
		result.put(KEY_STORETYPE, getStoreType());
		result.put(KEY_DESCRIPT, getDescription());
		result.put(KEY_ADDRESS, getAddress());
		result.put(KEY_MODTIME, getModTime());
		
		return result;
	}
}
