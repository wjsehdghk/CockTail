package com.knj.cocktail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knj.cocktail.dao.BeaconDAO;
import com.knj.cocktail.domain.Beacon;

@Service("BeaconService")
public class BeaconService {

	private BeaconDAO beaconDAO;
	
	@Autowired
	public void setBeaconDAO(BeaconDAO beaconDAO) {
		this.beaconDAO = beaconDAO;
	}
	
	public List<Beacon> selectBeaconList() {
		return beaconDAO.getBeacons();
	}

	@Transactional
	public void doAdd(String beaconId, String sectorId, String distance) {
		
		beaconDAO.insertBeacon(beaconId,sectorId,distance);
		
	}
	
	@Transactional
	public void doDelete(String beaconId) {
		
		beaconDAO.deleteBeacon(beaconId);
		
	}

}
