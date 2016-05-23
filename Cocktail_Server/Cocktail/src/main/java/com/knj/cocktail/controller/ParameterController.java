package com.knj.cocktail.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.knj.cocktail.domain.Parameter;
import com.knj.cocktail.service.ParameterService;

@Controller
public class ParameterController {

	private ParameterService parameterService;
	
	@Autowired
	public void setParameterService(ParameterService parameterService){
	this.parameterService = parameterService;
	}
	
	
	@RequestMapping("showSector")
	public String showHome(Model model){
		List<Parameter> parameterDefault = parameterService.selectDefaultList();
		model.addAttribute("parameterDefault", parameterDefault);
		
		return "sector";
	}
	
	@RequestMapping("insertSector")
	public String insertSector( HttpServletRequest request){
		String sectorId = request.getParameter("sectorId");
		String brightness = request.getParameter("brightness");
		String modeId = request.getParameter("modeId");
		String callId = request.getParameter("callId");
		
		System.out.println(sectorId);
		if(sectorId!="" && brightness != "" && modeId != "" && callId != ""){

			parameterService.doAdd(sectorId, brightness, modeId, callId);
			
			return "redirect:showSector";
		}
		else {
			return "failInsert";
		
		}
		}
	
	@RequestMapping("{sectorId}Delete")
	public String sectorDelete(@PathVariable("sectorId") String sectorId){
		
		parameterService.doDelete(sectorId);
		return "redirect:showSector";
	}
	
	@RequestMapping("test")
	public String showLogin(Model model) {
		System.out.println("success");
		return "test";
	}
}
