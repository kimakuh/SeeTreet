package com.seetreet.bean;

public class LocationBean {
	private String name;
	private String description;
	private double[] location;
	
	
	public LocationBean(String _name, String _description , double[] _location) {
		// TODO Auto-generated constructor stub
		this.name = _name;
		this.description = _description;
		location = _location;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double[] getLocation() {
		return location;
	}
	
	
	public String getName() {
		return name;
	}
}
