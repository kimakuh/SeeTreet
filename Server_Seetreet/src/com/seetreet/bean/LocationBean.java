package com.seetreet.bean;

public class LocationBean {
	private String name;
	private String description;
	private double latitude;
	private double longitude;
	
	public static final String KEY_LATITUDE 	= "l_lat";
	public static final String KEY_LONGITUDE 	= "l_long";
	public static final String KEY_NAME			= "name";
	public static final String KEY_DESCRIPT 	= "description";
	public static final String KEY_COORDINATE	= "coordinates";
	
	/* latitude  : maximum -> 120
	 * longitude : maximum -> 90
	 * 
	 * */
	public static final int LAT = 1;
	public static final int LONG = 0;
	
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
