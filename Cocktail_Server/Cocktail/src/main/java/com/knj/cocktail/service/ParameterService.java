package com.knj.cocktail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knj.cocktail.dao.ParameterDAO;
import com.knj.cocktail.domain.Custom;
import com.knj.cocktail.domain.Parameter;



@Service("ParameterService")
public class ParameterService {

	private ParameterDAO parameterDAO;
	
	@Autowired
	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}
	
	public List<Parameter> selectDefaultList() {
		return parameterDAO.getparameters();
	}

	@Transactional
	public void doAdd(String sectorId, String brightness, String modeId, String callId) {
		
		parameterDAO.insertParameter(sectorId,brightness,modeId,callId);
		
	}
	
	@Transactional
	public void doDelete(String sectorId) {
		
		parameterDAO.deleteParameter(sectorId);
		
	}

	public Parameter selectDefault() {
		return parameterDAO.getparameter();
	}

	public Custom selectCustom() {
		List<Custom> custom = parameterDAO.getCustoms();
		
		return null;
	}

}
