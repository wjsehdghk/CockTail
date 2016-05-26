package com.knj.cocktail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.knj.cocktail.domain.Beacon;

public class BeaconMapper implements RowMapper<Beacon> {


	public Beacon mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Beacon beacon = new Beacon();
		beacon.setBeaconId((rs.getInt("beaconId")));
		beacon.setSectorId((rs.getString("sectorId")));
		beacon.setDistance((rs.getInt("distance")));
		
	
		return beacon;
	
}
}