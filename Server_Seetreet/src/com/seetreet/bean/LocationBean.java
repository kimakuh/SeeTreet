package com.seetreet.bean;

public class LocationBean {
	private String name;
	private String description;
	private double latitude;
	private double longitude;
	
	public LocationBean(String name, String description , double latitude, double longitude) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String getName() {
		return name;
	}
}
