package com.knj.cocktail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knj.cocktail.dao.AdminDAO;
import com.knj.cocktail.domain.Admin;

@Service("AdminService")
public class AdminService {

	private AdminDAO adminDAO;

	@Autowired
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	
	public Admin isAdminOk(String adminId, String password){
		Admin admin = new Admin();
		admin = adminDAO.getAdmin(adminId);
		if(admin!=null && password.equals(admin.getPassword())){
			return admin;
		}
		else{
			return null;
		}
		}

}
