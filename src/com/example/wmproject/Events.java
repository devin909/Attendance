package com.example.wmproject;

public class Events {
	private String title;
	private String description;
	private String id;
	public Events(){
		
	}
	public String getTitle(){
		return title;
	}
	public String getDescription(){
		return description;
		
	}
	public String getId(){
		return id;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public void setId(String id){
		this.id=id;
	}
}
