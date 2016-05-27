package com.knj.cocktail.domain;

public class Parameter {

	
	public Parameter() {
	}
	
	private String sectorId;
	private int brightness;
	private int modeId;
	private int callId;
	
	public Parameter(String sectorId, int brightness, int modeId, int callId) {
		this.sectorId = sectorId;
		this.brightness = brightness;
		this.modeId = modeId;
		this.callId = callId;
	}

	public String getSectorId() {
		return sectorId;
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	public int getBrightness() {
		return brightness;
	}
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	public int getModeId() {
		return modeId;
	}
	public void setModeId(int modeId) {
		this.modeId = modeId;
	}
	public int getCallId() {
		return callId;
	}
	public void setCallId(int callId) {
		this.callId = callId;
	}
	//\"userId\"=\"" + userId + "\",
	@Override
	public String toString() {
		
		return "{\"sectorId\"=\"" + sectorId + "\", "+ "\"brightness\"=\"" + brightness + "\", \"modeId\"=\"" + modeId + "\", \"callId\"=\"" +callId +"\"}"; 
	}
	
	
	//"{\"sectorId\"=\"" + sectorId + "\", "+ "\"brightness\"=\"" + brightness + "\", \"modeId\"=\"" + modeId + "\", \"callId\"=\"" +callId +"\"}";
	  /*
	@Override
	   public String toString() {
	      return "{\"id\"=\"" + id + "\", \"name\"=\"" + name + "\", \"memberUrl\"=\"" + memberUrl + "\", \"lastReviewDate\"=\"" + lastReviewDate
	            + "\", \"phoneNumber\"=\"" + phoneNumber + "\", \"point\"=" + point + ", \"level\"=\"" + level + "\", \"reviewCount\"="
	            + reviewCount + ", \"totalReviewCount\"=" + totalReviewCount + "}";
	   }*/
}
