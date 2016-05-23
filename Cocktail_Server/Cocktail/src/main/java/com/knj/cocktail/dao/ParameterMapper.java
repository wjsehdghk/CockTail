package com.knj.cocktail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.knj.cocktail.domain.Parameter;

public class ParameterMapper implements RowMapper<Parameter> {


	public Parameter mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Parameter parameter = new Parameter();
		parameter.setUserId((rs.getString("userId")));
		parameter.setSectorId((rs.getString("sectorId")));
		parameter.setBrightness((rs.getInt("brightness")));
		parameter.setModeId((rs.getInt("modeId")));
		parameter.setCallId((rs.getInt("callId")));
	
		return parameter;
	
}
}