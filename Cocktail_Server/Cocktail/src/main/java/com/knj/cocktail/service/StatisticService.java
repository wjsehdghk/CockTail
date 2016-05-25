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
		
		double total = useCount.getCinema() + useCount.getExhibition() + useCount.getLibrary();
		useCount.setCinemaCompute((int)((useCount.getCinema()/total)*100));
		useCount.setExhibitionCompute((int)((useCount.getExhibition()/total)*100));
		useCount.setLibraryCompute( (int)((useCount.getLibrary()/total)*100));
		System.out.println("cinema="+useCount.getCinema());
		return useCount;
	}

}
