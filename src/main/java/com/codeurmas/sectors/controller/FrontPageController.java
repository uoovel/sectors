package com.codeurmas.sectors.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
			List<String> typeNameList = Arrays.asList("Manufacturing", "Other", "Service");
			for (String typeName : typeNameList) {
				SectorType sectorType = new SectorType();
			    sectorType.setTypeName(typeName);		
			    SectorType sectorTypeSaved = sectorTypeService.save(sectorType);
			    List<String> sectorList = null;
			    if(typeName == "Manufacturing") {
			    	sectorList = Arrays.asList(
				    		"Construction materials",
				    		"Electronics and Optics",
				    		"Food and Beverage",
				    		"Furniture",
				    		"Machinery",
				    		"Metalworking",
				    		"Plastic abd Rubber",
				    		"Printing",
				    		"Textile and Clothing",
				    		"Wood");
			    }
			    
			    if(typeName == "Other") {
			    	sectorList = Arrays.asList(
				    		"Creative industries",
				    		"Energy technology",
				    		"Environment");
			    }
			    
			    if(typeName == "Service") {
			    	sectorList = Arrays.asList(
				    		"Business services",
				    		"Engineering",
				    		"Information Technology and Telecommunications",
				    		"Tourism",
				    		"Translation services",
				    		"Transport and Logistics");
			    }
			    
			    for (String sectorName: sectorList) {
			    	SectorType sector = new SectorType();
				    sector.setTypeName(sectorName);
				    sector.setGranny(sectorTypeSaved);
				    SectorType sectorSaved = sectorTypeService.save(sector);
			    }
			}
			
		}
		
		
		
		
		//db create	
		
		System.out.println("FrontPageController100:");
		//Person person = new Person();
		//model.addAttribute("person", person);
		FrontDto frontDto = new FrontDto();
		model.addAttribute("frontDto", frontDto);		
	    
		List<SectorType> corrSectorTypeList = new ArrayList<>();
		corrSectorTypeList = giveFinalSectorList();
		
		
		model.addAttribute("sectorTypeList", corrSectorTypeList);
		return "index.html";
	}
	
	private List<SectorType> giveFinalSectorList() {
		//give final sector list
		List<SectorType> sectorTypeList = new ArrayList<>();
		List<SectorType> corrSectorTypeList = new ArrayList<>();
		sectorTypeList = sectorTypeService.findAll();		
		
		for (SectorType type :  sectorTypeList) {			
			Long id;
			try {
				id = type.getGranny().getId();
				if(id > 0) {
					String fnameAdding = "&nbsp;&nbsp;&nbsp;&nbsp;"; //järg
					type.setTypeName("<a>" + fnameAdding + type.getTypeName() + "</a>");					
				}				
				System.out.println(type.getTypeName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} 
			corrSectorTypeList.add(type);			
		}
		//give final sector list
		return corrSectorTypeList;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("frontDto") FrontDto frontDto, Model model) {
		Person person = frontDto.getPerson();
	    String personName = person.getName();
		System.out.println("FrontPageController200:" + personName);
		SectorType[] sectorTypes = frontDto.getSectors();
		Boolean fillOK = false;
		
		if(personName.length() > 0 && sectorTypes.length > 0) {
			fillOK = true;
		}
		if(fillOK == false) {
			System.out.println("FrontPageController300: Check fields!! ");
			List<SectorType> sectorTypeList = new ArrayList<>();
			sectorTypeList = sectorTypeService.findAll();
			model.addAttribute("frontDto", frontDto);
			model.addAttribute("sectorTypeList", giveFinalSectorList());
			return "index.html";
		}		
		
		Person personSaved = personService.save(person);
		
		PersonInSector personInSector = new PersonInSector();		
		for(int i = 0; i < sectorTypes.length; i++) {
			personInSector.setPerson(personSaved);
			personInSector.setSectorType(sectorTypes[i]);
			PersonInSector personInSectorSaved = personInSectorService.save(personInSector);
			System.out.println("FrontPageController400: " + personInSectorSaved.getId());
		}
		
		
		
		return "redirect:/";
		
	}
	
}
