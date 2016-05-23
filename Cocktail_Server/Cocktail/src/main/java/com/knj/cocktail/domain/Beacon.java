package com.knj.cocktail.domain;

public class Beacon {

	
	private int beaconId;
	private String sectorId;
	private int distance;
	
	public int getBeaconId() {
		return beaconId;
	}
	public void setBeaconId(int beaconId) {
		this.beaconId = beaconId;
	}
	public String getSectorId() {
		return sectorId;
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
}
