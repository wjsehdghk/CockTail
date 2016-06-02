package com.knj.cocktail.domain;

public class Requirement {

	
	private String userId;
	private String context;
	private String sectorId;
	private String date;
	
	public Requirement() {

	}
	public Requirement(String userId, String context) {
		super();
		this.userId = userId;
		this.context = context;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	

	public Requirement(String userId, String sectorId, String context) {
		super();
		this.userId = userId;
		this.context = context;
		this.sectorId = sectorId;
	}
	public String getSectorId() {
		return sectorId;
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}