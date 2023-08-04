package com.codeurmas.sectors.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codeurmas.sectors.model.Person;
import com.codeurmas.sectors.model.SectorType;
import com.codeurmas.sectors.model.PersonInSector;
import com.codeurmas.sectors.service.PersonInSectorService;
import com.codeurmas.sectors.service.PersonService;
import com.codeurmas.sectors.service.SectorTypeService;
import com.codeurmas.sectors.service.dto.FrontDto;

@Controller
public class FrontPageController {
	@Autowired
	private PersonService personService;
	@Autowired
	private SectorTypeService sectorTypeService;
	@Autowired
	private PersonInSectorService personInSectorService;
	


	@RequestMapping("/")
	public String frontPage(Model model) {
		
		List<SectorType> sectorTypeList = new ArrayList<>();		
		sectorTypeList = sectorTypeService.findAll();
		
		if (sectorTypeList.size() < 1) { //db create			
			//create DB
			sectorTypeService.createDB();
		}
		
		FrontDto frontDto = new FrontDto();
		//List<SectorType> sectorTypeList = new ArrayList<>();
		//sectorTypeList = sectorTypeService.findAll();
		
		List<SectorType> corrSectorTypeList = new ArrayList<>();
		//
		corrSectorTypeList = sectorTypeService.giveFinalSectorList();
		model.addAttribute("frontDto", frontDto);
		model.addAttribute("corrSectorTypeList", corrSectorTypeList);
		return "index.html";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			@Valid @ModelAttribute("frontDto") FrontDto frontDto,			
			BindingResult result,
			Model model,
			@RequestParam(value="action", required=true) String action) {
		
		SectorType[] sectorTypes = frontDto.getSectors();
        if(sectorTypes.length < 1) {			
			ObjectError errorSector = new ObjectError("globalError", "{Check selection}");
			result.addError(errorSector);
		}
        if(!frontDto.isAgreeTerms()) {			
  			ObjectError errorTerms = new ObjectError("globalError", "{Check 'Agree to terms'}");
  			result.addError(errorTerms);
  		}
        
		if(result.hasErrors()) {
			System.out.println("FrontPageController150:");
		}
		
		//List<SectorType> corrSectorTypeList = giveFinalSectorList();
		String personName = frontDto.getName();		
		Boolean fillOK = false;		
		if(personName.length() > 0 && sectorTypes.length > 0 && !result.hasErrors()) {
			fillOK = true;
		}
		if(fillOK == false) {
			
			model.addAttribute("corrSectorTypeList", sectorTypeService.giveFinalSectorList());
			return "index.html";
		}
		
		
		//confirm session
        //SectorType[] sectorTypes = frontDto.getSectors();		
		Person person = new Person();		
		person.setName(frontDto.getName());
		Person personSaved = new Person();
		Person personEdited = new Person();
		if(action.matches("Save")) {
			personSaved = personService.save(person);
		}
		if(action.matches("Edit")) {
			Long personId = frontDto.getPersonId();
			person.setId(personId);
			personEdited = personService.save(person);
			//Clear previously selected sections
			personInSectorService.deleteByPerson(person);
		}
		for(int i = 0; i < sectorTypes.length; i++) {
			PersonInSector personInSector = new PersonInSector();
			personInSector.setSectorType(sectorTypes[i]);
			personInSector.setAgreeTerms(true);
			Long personId = null;
			Long personInSectorId = null;
			if(action.matches("Save")) {
				
				personInSector.setPerson(personSaved);
				System.out.println("PersonInSector100: " + personInSector);
				PersonInSector personInSectorSaved = personInSectorService.save(personInSector);
				personId = personSaved.getId();
				personInSectorId = personInSectorSaved.getId();
				
			}
			if(action.matches("Edit")) {
				personInSector.setPerson(personEdited);
				PersonInSector personInSectorEdited = personInSectorService.edit(personInSector);
				personId = personEdited.getId();
				personInSectorId = personInSectorEdited.getId();
			}
			
			frontDto.setPersonId(personId);
			frontDto.setPersonInSectorId(personInSectorId);
			
			//frontDto = personInSectorService.confirmSession(frontDto, corrSectorTypeList, action);
			
			int finalConfirmButton = 1;
			model.addAttribute("finalConfirmButton", finalConfirmButton);
			if(i == sectorTypes.length - 1) {
				if (action.matches("Save") || action.matches("Edit")) {
					
					model.addAttribute("corrSectorTypeList", sectorTypeService.giveFinalSectorList());
					return "index.html";
				}
			}
		}
		
		return "redirect:/";
	}
	
	
	
	
}
