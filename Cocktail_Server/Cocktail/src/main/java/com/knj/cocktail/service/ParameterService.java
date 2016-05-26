package com.knj.cocktail.service;

import java.util.Iterator;
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

	
	
	
	
	public List<Custom> selectCustom() {
		List<Custom> custom = parameterDAO.getCustoms();
		Custom [] customs = new Custom [custom.size()];
		System.out.println("ctotal");
		int ctotal=0, etotal=0, ltotal=0;
		int cbrightness=0, ebrightness=0, lbrightness=0;
		int cmodeId=0, emodeId=0, lmodeId=0;
		int ccallId=0, ecallId=0, lcallId=0;
		for(int i=0;i<custom.size();i++){
			customs[i] = custom.get(i);
			if(customs[i].getSectorId().equals("cinema")){
				ctotal++;
				cbrightness+=customs[i].getBrightness();
				cmodeId+=customs[i].getModeId();
				ccallId+=customs[i].getCallId();
				
			}
			else if(customs[i].getSectorId().equals("exhibition")){
				etotal++;
				ebrightness+=customs[i].getBrightness();
				emodeId+=customs[i].getModeId();
				ecallId+=customs[i].getCallId();
			}
			else {
				ltotal++;
				lbrightness+=customs[i].getBrightness();
				lmodeId+=customs[i].getModeId();
				lcallId+=customs[i].getCallId();
			}
			
		}
		System.out.println("ctotal="+ctotal);
		System.out.println("etotal="+etotal);
		System.out.println("ltotal="+ltotal);
		custom.clear();
		Custom c = new Custom("cinema",cbrightness/ctotal,cmodeId/ctotal,ccallId/ctotal);
		Custom e = new Custom("exhibition",ebrightness/etotal,emodeId/etotal,ecallId/etotal);
		Custom l = new Custom("library",lbrightness/ltotal,lmodeId/ltotal,lcallId/ltotal);
		custom.add(c);
		custom.add(e);
		custom.add(l);
		return custom;
	}

}
