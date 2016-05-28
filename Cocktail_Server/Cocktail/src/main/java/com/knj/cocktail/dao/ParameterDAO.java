package com.knj.cocktail.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.knj.cocktail.domain.Custom;
import com.knj.cocktail.domain.Parameter;


@Component("ParameterDAO")
public class ParameterDAO {

	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<Parameter> getparameters() {

		String sqlStatement = "select * from parameter " ;
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
	

	public Parameter getParameter(String sectorId) {
		String sqlStatement = "select * from parameter where sectorId=?" ;
		try{
		return jdbcTemplateObject.queryForObject(sqlStatement, new Object[] {sectorId}, new ParameterMapper());
		}catch(EmptyResultDataAccessException e) {
	        // EmptyResultDataAccessException 예외 발생시 null 리턴
	        return null;
	    }
		}

	

	public List<Custom> getCustoms() {
		
		String sqlStatement = "select * from custom " ;
		return jdbcTemplateObject.query(sqlStatement, new CustomMapper());
	}

	public void insertCustom(Custom custom) {
		
		String sqlStatement = "insert into custom(userId,sectorId,brightness,modeId,callId) values(?,?, ? ,? ,?)  ";
		jdbcTemplateObject.update(sqlStatement, new Object[] {custom.getUserId(),custom.getSectorId(),custom.getBrightness(),custom.getModeId(),custom.getCallId()});
		
	}


}
