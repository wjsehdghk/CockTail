package com.knj.cocktail.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.knj.cocktail.domain.Admin;



	@Component("adminDAO")
	public class AdminDAO {

		private JdbcTemplate jdbcTemplateObject;

		@Autowired
		public void setDataSource(DataSource dataSource) {
			this.jdbcTemplateObject = new JdbcTemplate(dataSource);
		}

		public Admin getAdmin(String adminId){
			try{
			String sqlStatement = "select * from admin where adminId=?";
			return jdbcTemplateObject.queryForObject(sqlStatement, new Object[] {adminId}, new AdminMapper());
			}catch (Exception e){
				
				System.out.println("DAO 예외 처리 발생 확인 메세지");
				e.printStackTrace();
				return null;
			
			}
		
		}
	}

	