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

import com.codeurmas.sectors.error.Validation;
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

		List<SectorType> corrSectorTypeList = new ArrayList<>();
		
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
	
		result = Validation.validateForm(frontDto, result);
		
		if(result.hasErrors()) {			
			model.addAttribute("corrSectorTypeList", sectorTypeService.giveFinalSectorList());
			return "index.html";
		}
		
		frontDto = personInSectorService.saveSelections(frontDto, action);
		
		if (action.matches("Save") || action.matches("Edit")) {
			int finalConfirmButton = 1;
			model.addAttribute("finalConfirmButton", finalConfirmButton);
			model.addAttribute("corrSectorTypeList", sectorTypeService.giveFinalSectorList());
			return "index.html";
		}
		
		return "redirect:/";
	}
	
}
