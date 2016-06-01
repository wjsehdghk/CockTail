package com.knj.cocktail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knj.cocktail.dao.RequirementDAO;
import com.knj.cocktail.domain.Custom;
import com.knj.cocktail.domain.Requirement;

@Service("RequirementService")
public class RequirementService {
	
	private RequirementDAO requirementDAO;

	@Autowired
	public void setRequirementDAO(RequirementDAO requirementDAO) {
		this.requirementDAO = requirementDAO;
	}

	public void addRequirement(Requirement requirement) {
		requirementDAO.insertRequirement(requirement);
	}

	public List<Requirement> selectRequirementList() {
		List<Requirement> requirement= requirementDAO.getRequirements();
		return requirement;
	}

}
