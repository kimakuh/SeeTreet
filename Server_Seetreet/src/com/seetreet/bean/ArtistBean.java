package com.seetreet.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonNull;
import com.seetreet.bean.content.ContentBean;

public class ArtistBean implements BeanJson{
	private String		name;
	private String 		artistId;
	private String[] 	artistImages;
	private String 		videoUrl;
	private String 		description;
	private String		modTime;
	private GenreBean[]	genre;
	private LocationBean[] favoriteLocation;
	private ContentBean[] history;	
	
	public static final String KEY_NAME = "name";
	public static final String KEY_IMAGES = "artistImages";
	public static final String KEY_VIDEO  = "videoUrl";
	public static final String KEY_DESCRIPT = "description";
	public static final String KEY_GENRE = "artistGenre";
	public static final String KEY_LOCATIONS = "favoriteLocation";
	public static final String KEY_ID = "_id"; 
	public static final String KEY_MODTIME = "modTime";
	public static final String KEY_CONTENTHISTORY = "contentHistory";
	
	public ArtistBean(String name, String[] artistImages, String videoUrl , String description) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.artistImages = artistImages;
		this.videoUrl = videoUrl;
		this.description = description;
	}
	
	public ArtistBean(String[] artistImages, String name, String videoUrl , String description, String modTime,
			LocationBean[] locations , GenreBean[] genre) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.artistImages = artistImages;
		this.videoUrl = videoUrl;
		this.description = description;
		this.modTime = modTime;
		this.favoriteLocation = locations;
		this.genre = genre;
	}
	
	public ArtistBean(String[] artistImages, String name, String videoUrl , String description, String modTime,
			LocationBean[] locations, String artistId , ContentBean[] history , GenreBean[] genre) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.artistImages = artistImages;
		this.videoUrl = videoUrl;
		this.description = description;
		this.modTime = modTime;
		this.favoriteLocation = locations;
		this.artistId = artistId;
		this.history = history;
		this.genre = genre;
	}
	
	public String getName(){
		return name;
	}
	
	public String getArtistId() {
		return artistId;
	}
	public String[] getArtistImages() {
		return artistImages;
	}
	public String getDescription() {
		return description;
	}
	public LocationBean[] getFavoriteLocation() {
		return favoriteLocation;
	}
	public ContentBean[] getHistory() {
		return history;
	}
	public String getModTime() {
		return modTime;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	
	public GenreBean[] getGenre() {
		return genre;
	}
	
	@Override
	public JSONObject getJson() throws JSONException {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray images = new JSONArray();
		for(String image : getArtistImages()) {
			images.put(image);
		}
		result.put(KEY_NAME, getName())
			.put(KEY_IMAGES, images)
			.put(KEY_DESCRIPT, getDescription())
			.put(KEY_VIDEO, getVideoUrl());
		
		return result;
	}
	
}
