package com.knj.cocktail.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.knj.cocktail.domain.Requirement;

@Component("RequirementDAO")
public class RequirementDAO {

private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public void insertRequirement(Requirement requirement) {
		
		String sqlStatement = "insert into requirement(userId,context) values(?,?)  ";
		jdbcTemplateObject.update(sqlStatement, new Object[] {requirement.getUserId(),requirement.getContext()});
		
	}

	public List<Requirement> getRequirements() {
		String sqlStatement = "select * from requirement " ;
		return jdbcTemplateObject.query(sqlStatement, new RequirementMapper());
	}

}
