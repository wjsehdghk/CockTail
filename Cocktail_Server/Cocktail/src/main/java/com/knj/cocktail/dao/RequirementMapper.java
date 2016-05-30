package com.knj.cocktail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.knj.cocktail.domain.Requirement;

public class RequirementMapper implements RowMapper<Requirement> {


	public Requirement mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Requirement requirement = new Requirement();
		requirement.setUserId((rs.getString("userId")));
		requirement.setContext((rs.getString("context")));
		return requirement;
	
}
}