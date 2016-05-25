package com.knj.cocktail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.knj.cocktail.domain.UseCount;

public class UseCountMapper implements RowMapper<UseCount> {


	public UseCount mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		UseCount useCount = new UseCount();
		useCount.setCinema((rs.getInt("cinema")));
		useCount.setExhibition((rs.getInt("exhibition")));
		useCount.setLibrary((rs.getInt("library")));

	
		return useCount;
	
}
}