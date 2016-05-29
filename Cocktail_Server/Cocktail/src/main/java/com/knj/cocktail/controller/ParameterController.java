package com.knj.cocktail.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.knj.cocktail.domain.Custom;
import com.knj.cocktail.domain.Parameter;
import com.knj.cocktail.service.ParameterService;

import net.sf.json.JSONArray;

@Controller
public class ParameterController {

	private ParameterService parameterService;
	
	@Autowired
	public void setParameterService(ParameterService parameterService){
	this.parameterService = parameterService;
	}
	
	@RequestMapping("users")
	public String showUsers(Model model){
		List<Custom> logList = parameterService.selectCustom();
		model.addAttribute("logList", logList);
		return "users";
	}
	
	@RequestMapping("showSector")
	public String showHome(Model model){
		List<Parameter> parameterDefault = parameterService.selectDefaultList();
		model.addAttribute("parameterDefault", parameterDefault);
		return "sector";
	}

	@RequestMapping("sectorTest")
	public String showSectorTest(Model model){
		System.out.println("sectorTest");
		List<Parameter> parameterDefault = parameterService.selectDefaultList();

		model.addAttribute("parameter", parameterDefault);
		JSONArray jsonArray = new JSONArray();
		  model.addAttribute("jsonList", jsonArray.fromObject(parameterDefault));
		
		return "sectorTest";
	}

	
	
	@RequestMapping("insertSector")
	public String insertSector( HttpServletRequest request){
		System.out.println("here0");
		String sectorId = request.getParameter("sectorId");
		String brightness = request.getParameter("brightness");
		String modeId = request.getParameter("modeId");
		String callId = request.getParameter("callId");
		System.out.println("here1");
		System.out.println("11111"+sectorId);

		if(sectorId !="" && brightness != "" && modeId != "" && callId != ""){
			System.out.println("here1-1");
			Parameter parameter = parameterService.selectDefault(sectorId);
			System.out.println("here1-2");
			if(parameter!= null && parameter.getSectorId().equals(sectorId)){
				System.out.println("overlap");
				return "insertOverlap";
			}
			
			
			parameterService.doAdd(sectorId, brightness, modeId, callId);
			System.out.println("here3");
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
		Parameter parameter = parameterService.selectDefault("cinema");
		model.addAttribute("parameter", parameter);
		return "test";
	}
	
	@RequestMapping("app/insertParameter")
	public String insertParameter(HttpServletRequest request){
		System.out.println("successInsert");
		
		String userId = request.getParameter("userId");
		System.out.println(userId);
		String sectorId = request.getParameter("sectorId");
		System.out.println(sectorId);
		int brightness =Integer.parseInt(request.getParameter("brightness"));
		int modeId = Integer.parseInt(request.getParameter("modeId"));
		int callId = Integer.parseInt(request.getParameter("callId"));
		
		
		Custom custom = new Custom(userId,sectorId,brightness,modeId,callId);
		parameterService.addCustom(custom);
		
		return "defaultTransport";
	}
}
