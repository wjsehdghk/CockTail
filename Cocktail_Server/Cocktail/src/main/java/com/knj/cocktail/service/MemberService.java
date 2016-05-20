package com.knj.cocktail.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knj.cocktail.dao.MemberDAO;
import com.knj.cocktail.domain.Member;



@Service("MemberService")
public class MemberService {

	private MemberDAO offerDAO;

	@Autowired
	public void setMemberDAO(MemberDAO offerDAO) {
		this.offerDAO = offerDAO;
	}
	
	public Member isMemberOk(String memberId, String password) throws Exception {
		
		Member member = new Member();
		member = offerDAO.getMember(memberId);
		if(member!=null && password.equals(member.getPassword())){
			return member;
		}
		else
		return null;
	}

}
