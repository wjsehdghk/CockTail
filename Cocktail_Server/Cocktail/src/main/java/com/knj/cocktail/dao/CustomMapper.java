package com.knj.cocktail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.knj.cocktail.domain.Custom;

public class CustomMapper implements RowMapper<Custom> {


	public Custom mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Custom custom = new Custom();
		custom.setUserId((rs.getString("userId")));
		custom.setSectorId((rs.getString("sectorId")));
		custom.setBrightness((rs.getInt("brightness")));
		custom.setModeId((rs.getInt("modeId")));
		custom.setCallId((rs.getInt("callId")));
		
	
		return custom;

}
}
