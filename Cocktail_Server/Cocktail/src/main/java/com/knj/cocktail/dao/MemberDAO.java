package com.knj.cocktail.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.knj.cocktail.domain.Member;

@Component("memberDAO")
public class MemberDAO {

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public Member getMember(String memberId){
		String sqlStatement = "select * from member where memberId=?";
		return jdbcTemplateObject.queryForObject(sqlStatement, new Object[] {memberId}, new MemberMapper());
	}

	public void incrementCoffeePoint(String memberId) {
		String sqlStatement = "update member set coffeePoint=coffeePoint+500 where memberId=?";
		jdbcTemplateObject.update(sqlStatement, new Object[] {memberId});
		
	}
	
	
}
