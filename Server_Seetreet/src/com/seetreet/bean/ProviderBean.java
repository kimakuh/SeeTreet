package com.seetreet.bean;

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
	private String modTime;
	
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
}
