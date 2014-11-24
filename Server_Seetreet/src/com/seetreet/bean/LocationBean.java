package com.seetreet.bean;

public class LocationBean {
	private String name;
	private String description;
	private double latitude;
	private double longitude;
	
	public static final String KEY_LATITUDE 	= "l_lat";
	public static final String KEY_LONGITUDE 	= "l_long";
	public static final String KEY_NAME			= "l_name";
	public static final String KEY_DESCRIPT 	= "l_descript";
	public static final String KEY_COORDINATE	= "coordinates";
	
	/* latitude  : maximum -> 120
	 * longitude : maximum -> 90
	 * 
	 * */
	public static final int LAT = 0;
	public static final int LONG = 1;
	
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
