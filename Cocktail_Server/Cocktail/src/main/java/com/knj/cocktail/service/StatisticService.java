package com.knj.cocktail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knj.cocktail.dao.StatisticDAO;
import com.knj.cocktail.domain.UseCount;

@Service("StatisticService")
public class StatisticService {
	
private StatisticDAO statisticDAO;
	
	@Autowired
	public void setStatisticDAO(StatisticDAO statisticDAO) {
		this.statisticDAO = statisticDAO;
	}
	
	public void doIncUseCount(String sectorId) {
		statisticDAO.updateUseCount(sectorId);
		
	}
	public UseCount showUseCount(){
		
		
		UseCount useCount = statisticDAO.selectUseCount();
		
		return useCount;
	}

	public UseCount doCompute(UseCount useCount) {
		
		int total = useCount.getCinema() + useCount.getExhibition() + useCount.getLibrary();
		useCount.setCinemaCompute(useCount.getCinema()*100/total);
		useCount.setExhibitionCompute(useCount.getExhibition()*100/total);
		useCount.setLibraryCompute(useCount.getLibrary()*100/total);
		System.out.println("cinema="+useCount.getCinema());
		return useCount;
	}

}
