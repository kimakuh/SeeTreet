package com.seetreet.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.seetreet.bean.content.ContentBean;

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
	
	public static final String KEY_ID = "_id";
	public static final String KEY_IMAGES = "providerImage";
	public static final String KEY_TYPE = "contentType";
	public static final String KEY_GENRE = "favoriteGenre";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_STORETITLE = "StoreTitle";
	public static final String KEY_STORETYPE = "StoreType";
	public static final String KEY_DESCRIPT = "description";
	public static final String KEY_MODTIME = "modTime";
	
	
	public ProviderBean(String contentType, String[] images ,LocationBean location, GenreBean[] genre, 
			String storeTitle, String storeType, String description) {
		// TODO Auto-generated constructor stub
		this.contentType = contentType;
		this.images = images;
		this.location = location;
		this.StoreTitle = storeTitle;
		this.StoreType = storeType;
		this.description = description;
		this.favoriteGenre = genre;
	}
	
	public ProviderBean(String contentType, String[] images ,LocationBean location, GenreBean[] genre, 
			String storeTitle, String storeType, String description , String modTime) {
		// TODO Auto-generated constructor stub
		this.contentType = contentType;
		this.images = images;
		this.location = location;
		this.StoreTitle = storeTitle;
		this.StoreType = storeType;
		this.description = description;
		this.favoriteGenre = genre;
	}

	
	public ProviderBean(String contentType, String[] images ,LocationBean location, 
			String storeTitle, String storeType, String description,
			String providerId, GenreBean[] genre, ContentBean[] history, String modTime) {
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
	}
	
	public ProviderBean(JSONObject obj){
		//怨듦났 API Bean 媛앹껜
		try{
			this.contentType = "public";
			if(obj.has("firstimage")){
				if(obj.has("firstimage2")){
					String[] tempImage = {obj.getString("firstimage"),obj.getString("firstimage2")};
					this.images = tempImage;
				}
				else{
					String[] tempImage = {obj.getString("firstimage"), "null"};
					this.images = tempImage;
				}
			}			
			if(obj.has("title")){
				this.StoreTitle = obj.getString("title");
			}
			if(obj.has("overview")){
				this.description = obj.getString("overview");
			}
			if(obj.has("mapx") && obj.has("mapy")){
				//double[] temp={obj.getDouble("mapx"), obj.getDouble("mapy")};
				//public LocationBean(String name, String description , double latitude, double longitude) {
				LocationBean loc = new LocationBean(this.StoreTitle, this.description, obj.getDouble("mapx"), obj.getDouble("mapy"));
				this.location = loc;
			}
			if(obj.has("cat3")){
				this.StoreType = ApiContentBean.getCategoryTocontentGenre(obj.getString("cat3"));
				this.publicGenre = ApiContentBean.getCategoryTocontentGenre(obj.getString("cat3"));
			}
			if(obj.has("modifiedtime")){
				this.modTime = obj.getString("modifiedtime");
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

	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray images = new JSONArray();
		JSONArray genres = new JSONArray();
		for(String image : getImages()) {
			images.put(image);
		}
		
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
		
		return result;
	}
}
