package com.knj.cocktail.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.knj.cocktail.domain.Parameter;


@Component("ParameterDAO")
public class ParameterDAO {

	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<Parameter> getparameters() {

		String sqlStatement = "select * from parameter where userId is null" ;
		return jdbcTemplateObject.query(sqlStatement, new ParameterMapper());
		
	}

	public void insertParameter(String sectorId, String brightness, String modeId, String callId) {

	
		String sqlStatement = "insert into parameter(sectorId,brightness,modeId,callId) values(?, ? ,? ,?)  ";
		jdbcTemplateObject.update(sqlStatement, new Object[] {sectorId,brightness,modeId,callId});
		}
	
	public void deleteParameter(String sectorId) {
		
		String sqlStatement = "delete from parameter where sectorId= ?";
		jdbcTemplateObject.update(sqlStatement, new Object[] {sectorId});
		
	}

	public Parameter getparameter() {
		String sqlStatement = "select * from parameter where userId is null && sectorId='cinema'" ;
		return jdbcTemplateObject.queryForObject(sqlStatement, new ParameterMapper());
	}

}
