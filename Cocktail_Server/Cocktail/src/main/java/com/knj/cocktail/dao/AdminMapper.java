package com.knj.cocktail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.knj.cocktail.domain.Admin;


public class AdminMapper implements RowMapper<Admin> {


	public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Admin admin = new Admin();
		admin.setAdminId(rs.getString("adminId"));
		admin.setPassword(rs.getString("password"));
	
		return admin;
	
}

}