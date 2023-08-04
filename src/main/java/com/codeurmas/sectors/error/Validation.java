package com.codeurmas.sectors.error;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.codeurmas.sectors.model.SectorType;
import com.codeurmas.sectors.service.dto.FrontDto;

public class Validation {

	public static BindingResult validateForm(@Valid FrontDto frontDto, BindingResult result) {
		SectorType[] sectorTypes = frontDto.getSectors();
        if(sectorTypes.length < 1) {			
			ObjectError errorSector = new ObjectError("globalError", "{Check selection}");
			result.addError(errorSector);
		}
        if(!frontDto.isAgreeTerms()) {			
  			ObjectError errorTerms = new ObjectError("globalError", "{Check 'Agree to terms'}");
  			result.addError(errorTerms);
  		}
		String personName = frontDto.getName();		
		Boolean fillOK = false;		
		if(personName.length() > 0 && sectorTypes.length > 0 && !result.hasErrors()) {
			fillOK = true;
		}
		return result;
	}

}
