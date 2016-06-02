package com.knj.cocktail.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.knj.cocktail.domain.Requirement;
import com.knj.cocktail.service.RequirementService;



@Controller
public class RequirementController {
	
	private RequirementService  requirementService;
	
	@Autowired
	public void setParameterService( RequirementService  requirementService){
	this.requirementService =  requirementService;
	}
	
	@RequestMapping("showRequirement")
	public String showRequirement(Model model) {
		
		List<Requirement> requirementList = requirementService.selectRequirementList();
		model.addAttribute("requirementList", requirementList);
		
		return "requirement";
	}

	@RequestMapping("app/insertRequirement")
	public String insertRequirement(HttpServletRequest request) {
		System.out.println("test requirement");
		String userId = request.getParameter("userId");
		String sectorId =request.getParameter("sectorId");
		String context = request.getParameter("context");
		
		Requirement requirement = new Requirement(userId,sectorId,context);
		requirementService.addRequirement(requirement);
		
		return "defaultTransport";
	}
	
}
