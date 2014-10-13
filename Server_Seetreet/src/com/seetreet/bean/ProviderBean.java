package com.seetreet.bean;

import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.seetreet.bean.content.ContentBean;

public class ProviderBean {
	private String providerId;
	private String[] images;
	private String contentType;
	private GenreBean[] favoriteGenre;
	private LocationBean location;
	private String StoreTitle;
	private String StoreType;
	private String description;
	private ContentBean[] history;
	private Long modTime;
	private String publicGenre;
	
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
	public ProviderBean(String contentType, String[] images ,LocationBean location, 
			String storeTitle, String storeType, String description,
			String providerId, GenreBean[] genre, ContentBean[] history, Long modTime) {
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
		//공공 API Bean 객체
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
				double[] temp={obj.getDouble("mapx"), obj.getDouble("mapy")};
				LocationBean loc = new LocationBean(this.StoreTitle, this.description, temp);
				this.location = loc;
			}
			if(obj.has("cat3")){
				this.StoreType = ApiContentBean.getCategoryTocontentGenre(obj.getString("cat3"));
				this.publicGenre = ApiContentBean.getCategoryTocontentGenre(obj.getString("cat3"));
			}
			if(obj.has("modifiedtime")){
				this.modTime = obj.getLong("modifiedtime");
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
	public Long getModTime() {
			return modTime;
	}
	public String getPublicGenre() {
		return publicGenre;
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
	/*
	public BasicDBObject getProviderObject(ProviderBean obj) {
		
		BasicDBList imageList = new BasicDBList();
		String[] imageArray = obj.getImages();
		imageList.add(0, obj.getImages()[0]);
		imageList.add(0, obj.getImages()[1]);
		
		BasicDBList locationList = new BasicDBList();
		LocationBean locationBean = obj.getLocation();
		locationList.add(0, obj.getLocation().getLocation()[0]);
		locationList.add(1, obj.getLocation().getLocation()[1]);
		BasicDBObject local = new BasicDBObject();
		local.append("type", "Point")
		.append("coordinates", locationList);
		
		BasicDBObject result = new BasicDBObject();
		result.append("providerImage", imageList)
			  .append("contentType", obj.getContentType())
			  .append("favoriteGenre", obj.getFavoriteGenre())
			  .
			  
	}*/
}
