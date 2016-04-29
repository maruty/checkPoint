package com.blog.marublo;

public class Point {

	private String name = "";
	private String point = "";
	private String date = "";
	private String yesterday = "";



	public Point(){


	}

	public Point(String name, String point, String date){
		this.name = name;
		this.point = point;
		this.date = date;

	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getPoint() {
		return point;
	}





	public void setPoint(String point) {
		this.point = point;
	}





	public String getDate() {
		return date;
	}





	public void setDate(String date) {
		this.date = date;
	}


	public String getYesterday() {
		return yesterday;
	}

	public void setYesterday(String yesterday) {
		this.yesterday = yesterday;
	}




}
