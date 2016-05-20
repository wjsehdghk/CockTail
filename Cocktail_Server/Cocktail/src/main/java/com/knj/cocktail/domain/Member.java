package com.knj.cocktail.domain;

public class Member {

	private String memberId;
	private String password;
	private String coffeePoint;
	
	
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCoffeePoint() {
		return coffeePoint;
	}
	public void setCoffeePoint(String coffeePoint) {
		this.coffeePoint = coffeePoint;
	}
	
	public Member(String memberID, String password) {
		super();
		this.memberId = memberId;
		this.password = password;
	}
	public Member() {
	
	}
}
