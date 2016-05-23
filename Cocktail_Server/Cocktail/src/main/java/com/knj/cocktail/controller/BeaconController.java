package com.knj.cocktail.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.knj.cocktail.domain.Beacon;
import com.knj.cocktail.service.BeaconService;

@Controller
public class BeaconController {

	

		private BeaconService beaconService;
		
		@Autowired
		public void setBeaconService(BeaconService beaconService){
		this.beaconService = beaconService;
		}
		
		
		@RequestMapping("showBeacon")
		public String showHome(Model model){
			List<Beacon> beaconList = beaconService.selectBeaconList();
			model.addAttribute("beaconList", beaconList);
			
			return "beacon";
		}
		
		@RequestMapping("insertBeacon")
		public String insertBeacon( HttpServletRequest request){
			String beaconId = request.getParameter("beaconId");
			String sectorId = request.getParameter("sectorId");
			String distance = request.getParameter("distance");
			
			System.out.println(sectorId);
			if(beaconId!="" && sectorId!= "" && distance!= "" ){

				beaconService.doAdd(beaconId, sectorId, distance);
				
				return "redirect:showBeacon";
			}
			else {
				return "failInsert";
			
			}
			}
		
		@RequestMapping("{beaconId}Remove")
		public String beaconDelete(@PathVariable("beaconId") String beaconId){
			
			beaconService.doDelete(beaconId);
			return "redirect:showBeacon";
		}
		

	}
