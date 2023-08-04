package com.codeurmas.sectors.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeurmas.sectors.model.SectorType;
import com.codeurmas.sectors.repository.SectorTypeRepository;

@Service
@Transactional
public class SectorTypeService {
	
	@Autowired
	private SectorTypeRepository repo;

	public SectorType save(SectorType sectorType) {
		return repo.save(sectorType);
		
	}

	public List findAll() {		
		return repo.findAll();
	}
	
	public void createDB() {
		List<String> typeNameList = Arrays.asList("Manufacturing", "Other", "Service");
		for (String typeName : typeNameList) {
			SectorType sectorType = new SectorType();
		    sectorType.setTypeName(typeName);		
		    SectorType sectorTypeSaved = repo.save(sectorType);
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
			    SectorType sectorSaved = repo.save(sector);
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
			    
			    try {
					for (String subSectorName:subSectorList) {
						SectorType subSector = new SectorType();
					    
						subSector.setTypeName(subSectorName);
					    subSector.setParent(sectorSaved);
					    SectorType subSectorSaved = repo.save(subSector);
					
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
							    SectorType childSectorSaved = repo.save(childSector);
							}// for childSector
						} catch (Exception e) {
							
						}
					}//for subsector
				} catch (Exception e) {
					
				}
		    }//for sector
		}//for sectortype
		
	}
	
	public List<SectorType> giveFinalSectorList() {
		//give final sector list
		List<SectorType> sectorTypeList = new ArrayList<>();
		sectorTypeList = repo.findAll();
		List<SectorType> corrSectorTypeList = new ArrayList<>();
				
		
		for (SectorType type :  sectorTypeList) {
			SectorType typeCorr = new SectorType();
			Long idGranny = null;
			try {
				idGranny = type.getGranny().getId();
				if(idGranny > 0) {
					String fnameAdding = "&nbsp;&nbsp;&nbsp;&nbsp;";
					typeCorr.setTypeName("<a>" + fnameAdding + type.getTypeName() + "</a>");
					typeCorr.setGranny(type.getGranny());				}				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			Long idParent = null;
			try {
				idParent = type.getParent().getId();
				if(idParent > 0) {
					String fnameAdding = "&nbsp;&nbsp;&nbsp;&nbsp;";
					typeCorr.setTypeName("<a>" + fnameAdding + fnameAdding + type.getTypeName() + "</a>");					
					typeCorr.setParent(type.getParent());
				}				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			Long idChild = null;
			try {
				idChild = type.getChild().getId();
				if(idChild > 0) {
					String fnameAdding = "&nbsp;&nbsp;&nbsp;&nbsp;"; //järg
					typeCorr.setTypeName("<a>" + fnameAdding + fnameAdding + fnameAdding + type.getTypeName() + "</a>");					
					typeCorr.setChild(type.getChild());
				}				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			if(idGranny == null && idParent == null && idChild == null) {
				String fnameAdding = "&nbsp;&nbsp;&nbsp;&nbsp;";
				typeCorr.setTypeName("<a>" + type.getTypeName() + "</a>");					
			}
			typeCorr.setId(type.getId());
			corrSectorTypeList.add(typeCorr);			
		}
		//give final sector list
		return corrSectorTypeList;
	}
	

}
