package com.knj.cocktail.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.knj.cocktail.domain.Beacon;

@Component("BeaconDAO")
public class BeaconDAO {

	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<Beacon> getBeacons() {

		String sqlStatement = "select * from beacon" ;
		return jdbcTemplateObject.query(sqlStatement, new BeaconMapper());
		
	}

	public void insertBeacon(String beaconId, String sectorId, String distance) {

	
		String sqlStatement = "insert into beacon(beaconId,sectorId,distance) values(?, ? ,? )  ";
		jdbcTemplateObject.update(sqlStatement, new Object[] {beaconId,sectorId,distance});
		}
	
	public void deleteBeacon(String beaconId) {
		
		String sqlStatement = "delete from beacon where beaconId= ?";
		jdbcTemplateObject.update(sqlStatement, new Object[] {beaconId});
		
	}

}