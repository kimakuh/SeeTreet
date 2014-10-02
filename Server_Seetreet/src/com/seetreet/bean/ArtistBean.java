package com.seetreet.bean;

public class ArtistBean {
	private String 		artistId;
	private String[] 	artistImages;
	private String 		videoUrl;
	private String 		description;
	private String		modTime;
	private LocationBean[] favoriteLocation;
	private ContentBean[] history;	
	
	public ArtistBean(String[] artistImages, String videoUrl , String description, String modTime,
			LocationBean[] locations) {
		// TODO Auto-generated constructor stub
		this.artistImages = artistImages;
		this.videoUrl = videoUrl;
		this.description = description;
		this.modTime = modTime;
		this.favoriteLocation = locations;
	}
	
	public ArtistBean(String[] artistImages, String videoUrl , String description, String modTime,
			LocationBean[] locations, String artistId , ContentBean[] history) {
		// TODO Auto-generated constructor stub
		this.artistImages = artistImages;
		this.videoUrl = videoUrl;
		this.description = description;
		this.modTime = modTime;
		this.favoriteLocation = locations;
		this.artistId = artistId;
		this.history = history;
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
	
}
