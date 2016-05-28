package com.knj.cocktail.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.knj.cocktail.domain.Custom;
import com.knj.cocktail.domain.Parameter;
import com.knj.cocktail.domain.UseCount;
import com.knj.cocktail.service.ParameterService;
import com.knj.cocktail.service.StatisticService;



@Controller
public class StatisticContorller {

	private StatisticService statisticService;
	
	@Autowired
	public void setStatisticService(StatisticService statisticService){
	this.statisticService = statisticService;
	}
	
	private ParameterService parameterService;
	
	@Autowired
	public void setParameterService(ParameterService parameterService){
	this.parameterService = parameterService;
	}
	
	@RequestMapping("showStatistic")
	public String showHome(Model model){
		
		UseCount useCount = statisticService.showUseCount();
		useCount = statisticService.doCompute(useCount);
		model.addAttribute("useCount", useCount);
		List<Custom> custom = parameterService.selectCustomAverage();
		model.addAttribute("custom", custom);
		
		return "statistic";
	}
	
	@RequestMapping("app/incUseCount")
	public String incUseCount(Model model,HttpServletRequest request){
		
		System.out.println("successInc");
		String sectorId = request.getParameter("sectorId");
		System.out.println(sectorId);
		statisticService.doIncUseCount(sectorId);
		List<Parameter> parameter = parameterService.selectDefaultList();
		model.addAttribute("parameter", parameter);
		return "defaultTransport";
		
	}
	
	
}
