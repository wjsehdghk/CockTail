package com.knj.cocktail.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.knj.cocktail.domain.Admin;
import com.knj.cocktail.domain.UseCount;

@Component("StatisticDAO")
public class StatisticDAO {
	
private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void updateUseCount(String sectorId) {
		
		String sqlStatement = "update usecount set " +sectorId+"="+sectorId+"+1 where id='1'";
		jdbcTemplateObject.update(sqlStatement, new Object[]{});
		
	}

	public  UseCount selectUseCount() {
		String sqlStatement = "select * from usecount where id='1'";
		return jdbcTemplateObject.queryForObject(sqlStatement,new Object[] {}, new UseCountMapper());
		
	}
	
	

}

