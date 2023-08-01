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
		System.out.println("FrontPageController10:");
		List<SectorType> sectorTypeList = new ArrayList<>();
		System.out.println("FrontPageController20:");
		sectorTypeList = sectorTypeService.findAll();
		System.out.println("FrontPageController30:");
		if (sectorTypeList.size() < 1) { //db create
			System.out.println("FrontPageController40:");
			//create DB
			createDB();
			
			
		}
		
		
		
		
		//db create	
		
		System.out.println("FrontPageController100:");
		//Person person = new Person();
		//model.addAttribute("person", person);
		FrontDto frontDto = new FrontDto();
				
	    
		List<SectorType> corrSectorTypeList = new ArrayList<>();
		corrSectorTypeList = giveFinalSectorList();
		//frontDto.corrSectorTypeList = corrSectorTypeList;
		model.addAttribute("frontDto", frontDto);
		model.addAttribute("corrSectorTypeList", corrSectorTypeList);
		return "index.html";
	}
	
	private void createDB() {
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
			    		"Plastic and Rubber",
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
			    //add subsectors
			    List<String> subSectorList = null;
			    if(sectorName == "Food and Beverage") {
			    	subSectorList = Arrays.asList(
				    "Bakery & confectionery products",
				    "Beverages",
				    "Fish & fish products",
				    "Meat & meat products",
				    "Milk & dairy products",
				    "Other",
				    "Sweets & snack food");
			    }
			    if(sectorName == "Furniture") {
			    	subSectorList = Arrays.asList(
				    "Bathroom/sauna",
				    "Bedroom",
				    "Childrens room",
				    "Kitchen",
				    "Living room",
				    "Office",
				    "Other (Furniture)",
				    "Outdoor",
				    "Project furniture");
			    }
			    if(sectorName == "Machinery") {
			    	subSectorList = Arrays.asList(
				    "Machinery components",
				    "Machinery equipment/tools",
				    "Manufacture of machinery",
				    "Maritime",
				    "Metal structures",
				    "Other",
				    "Repair and maintenance service");//fourth level: maritime
			    }
			    if(sectorName == "Metalworking") {//fourth: metal works
			    	subSectorList = Arrays.asList(
				    "Construction of metal structures",
				    "Houses and buildings",
				    "Metal products",
				    "Metal works");
			    }
			    if(sectorName == "Plastic and Rubber") {//four: plastic processing
			    	subSectorList = Arrays.asList(
				    "Packaging",
				    "Plastic goods",
				    "Plastic processing technology",
				    "Plastic profiles");
			    }
			    if(sectorName == "Printing") {
			    	subSectorList = Arrays.asList(
				    "Advertising",
				    "Book/Periodicals printing",
				    "Labelling and packaging printing");
			    }
			    if(sectorName == "Textile and Clothing") {
			    	subSectorList = Arrays.asList(
				    "Clothing",
				    "Textile");
			    }
			    if(sectorName == "Wood") {
			    	subSectorList = Arrays.asList(
				    "Other (Wood)",
				    "Wooden building materials",
				    "Wooden houses");
			    }
			    if(sectorName == "Information Technology and Telecommunications") {
			    	subSectorList = Arrays.asList(
				    "Data processing, Web portals, E-marketing",
				    "Programming, Consultancy",
				    "Software, Hardware",
				    "Telecommunications");
			    }
			    if(sectorName == "Transport and Logistics") {
			    	subSectorList = Arrays.asList(
				    "Air",
				    "Rail",
				    "Road",
				    "Water");
			    }
			    //System.out.println("createDB100: " + sectorName);
			    
			    try {
					for (String subSectorName:subSectorList) {
						SectorType subSector = new SectorType();
					    
						subSector.setTypeName(subSectorName);
					    subSector.setParent(sectorSaved);
					    SectorType subSectorSaved = sectorTypeService.save(subSector);
					
					    //add childsectors
					    List<String> childSectorList = null;
					    if(subSectorName == "Maritime") {
					    	childSectorList = Arrays.asList(
						    "Aluminium and steel workboats",
						    "Boat/Yacht building",
						    "Ship repair and conversion");
					    }
					    if(subSectorName == "Metal works") {
					    	childSectorList = Arrays.asList(
						    "CNC-machining",
						    "Forgings, Fasteners",
						    "Gas, Plasma, Laser cutting",
						    "MIG, TIG, Aluminium welding");
					    }
					    if(subSectorName == "Plastic processing technology") {
					    	childSectorList = Arrays.asList(
						    "Blowing",
						    "Moulding",
						    "Plastics welding and processing");
					    }
					
					    try {
							for (String childSectorName:childSectorList) {
								SectorType childSector = new SectorType();
							    childSector.setTypeName(childSectorName);
							    childSector.setChild(subSectorSaved);
							    SectorType childSectorSaved = sectorTypeService.save(childSector);
							}// for childSector
						} catch (Exception e) {
							
						}
					
					
					}//for subsector
				} catch (Exception e) {
					
				}
			    
				    
			   
			    
			    
		    }//for sector
		}//for sectortype
		
	}

	private List<SectorType> giveFinalSectorList() {
		//give final sector list
		List<SectorType> sectorTypeList = new ArrayList<>();
		List<SectorType> corrSectorTypeList = new ArrayList<>();
		sectorTypeList = sectorTypeService.findAll();		
		
		for (SectorType type :  sectorTypeList) {			
			Long idGranny;
			try {
				idGranny = type.getGranny().getId();
				if(idGranny > 0) {
					String fnameAdding = "&nbsp;&nbsp;&nbsp;&nbsp;"; //järg
					type.setTypeName("<a>" + fnameAdding + type.getTypeName() + "</a>");					
				}				
				//System.out.println(type.getTypeName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			Long idParent;
			try {
				idParent = type.getParent().getId();
				if(idParent > 0) {
					String fnameAdding = "&nbsp;&nbsp;&nbsp;&nbsp;"; //järg
					type.setTypeName("<a>" + fnameAdding + fnameAdding + type.getTypeName() + "</a>");					
				}				
				//System.out.println(type.getTypeName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			Long idChild;
			try {
				idChild = type.getChild().getId();
				if(idChild > 0) {
					String fnameAdding = "&nbsp;&nbsp;&nbsp;&nbsp;"; //järg
					type.setTypeName("<a>" + fnameAdding + fnameAdding + fnameAdding + type.getTypeName() + "</a>");					
				}				
				//System.out.println(type.getTypeName());
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
        System.out.println("FrontPageController100: " + frontDto.isAgreeTerms());
		if(result.hasErrors()) {
			System.out.println("FrontPageController150:");
		}
		
		//Person person = frontDto.getPerson();
	    String personName = frontDto.getName();
		System.out.println("FrontPageController200:" + result.toString());
		
		
		
		Boolean fillOK = false;
		
		if(personName.length() > 0 && sectorTypes.length > 0 && !result.hasErrors()) {
			fillOK = true;
		}
		if(fillOK == false) {
			System.out.println("FrontPageController300: Check fields!! ");
			//List<SectorType> sectorTypeList = new ArrayList<>();
			//sectorTypeList = sectorTypeService.findAll();
			//model.addAttribute("frontDto", frontDto);
	
			model.addAttribute("corrSectorTypeList", giveFinalSectorList());
			return "index.html";
		}		
		Person person = new Person();		
		person.setName(personName);
		Person personSaved = new Person();
		Person personEdited = new Person();
		if(action.matches("Save")) {
			personSaved = personService.save(person);
		}
		if(action.matches("Edit")) {
			Long personId = frontDto.getPersonId();
			person.setId(personId);
			System.out.println("FrontPageController330: " + person);
			personEdited = personService.edit(person);
			//Clear previously selected sections
			personInSectorService.deleteByPerson(person);//järg		
		}
		
				
		for(int i = 0; i < sectorTypes.length; i++) {
			PersonInSector personInSector = new PersonInSector();
			personInSector.setSectorType(sectorTypes[i]);
			personInSector.setAgreeTerms(true);
			Long personId = null;
			Long personInSectorId = null;
			System.out.println("FrontPageController350: " + action);//järg
			if(action.matches("Save")) {
				
				//personSaved = personService.save(person);
				personInSector.setPerson(personSaved);
				System.out.println("FrontPageController370: " + personInSector);//järg
				PersonInSector personInSectorSaved = personInSectorService.save(personInSector);
				personId = personSaved.getId();
				personInSectorId = personInSectorSaved.getId();
				
				System.out.println("FrontPageController400: " + personInSectorId);
			}
			if(action.matches("Edit")) {
				
				System.out.println("FrontPageController500: " + personEdited);
				personInSector.setPerson(personEdited);
				
				
				PersonInSector personInSectorEdited = personInSectorService.edit(personInSector);
				personId = personEdited.getId();
				personInSectorId = personInSectorEdited.getId();
				System.out.println("FrontPageController600: " + personInSectorEdited);
			}
			//model.addAttribute("personId", personId);
			//model.addAttribute("personInSectorId", personInSectorId);
			frontDto.setPersonId(personId);
			frontDto.setPersonInSectorId(personInSectorId);
			
			int finalConfirmButton = 1;
			model.addAttribute("finalConfirmButton", finalConfirmButton);
			System.out.println("FrontPageController700: " + sectorTypes.length);
			if(i == sectorTypes.length - 1) {
				if (action.matches("Save") || action.matches("Edit")) {
					model.addAttribute("corrSectorTypeList", giveFinalSectorList());
					return "index.html";
				}
			}
			
			
		}
		
		
		
		
		return "redirect:/";
		
	}
	
}
