package com.knj.cocktail.domain;



public class Custom {

	

	private String userId;
	private String sectorId;
	private int brightness;
	private int modeId;
	private int callId;
	private String log;
	
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSectorId() {
		return sectorId;
	}
	public Custom() {
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
	
	public Custom( String sectorId, int brightness, int modeId, int callId) {
		this.sectorId = sectorId;
		this.brightness = brightness;
		this.modeId = modeId;
		this.callId = callId;
	}
	
	public Custom(String userId, String sectorId, int brightness, int modeId, int callId, String log) {
		super();
		this.userId = userId;
		this.sectorId = sectorId;
		this.brightness = brightness;
		this.modeId = modeId;
		this.callId = callId;
		this.log = log;
	}
	public Custom(String userId, String sectorId, int brightness, int modeId, int callId) {
		super();
		this.userId = userId;
		this.sectorId = sectorId;
		this.brightness = brightness;
		this.modeId = modeId;
		this.callId = callId;
	}
}
