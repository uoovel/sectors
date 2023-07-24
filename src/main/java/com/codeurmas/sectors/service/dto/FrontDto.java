package com.codeurmas.sectors.service.dto;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.codeurmas.sectors.model.Person;
import com.codeurmas.sectors.model.SectorType;

public class FrontDto {
	
	//private Person person;
	
	//@Size(message= "{Check selection}")
	private SectorType[] sectors;
	
	//@NotEmpty(message= "Check selection")
	//private List<SectorType> sectors;
	
	
	@Length(min=1, max=100, message="{Check name}")
	private String name;
	
	public List<SectorType> corrSectorTypeList;
	
	
	
	
	


	public FrontDto() {
		
	}
	
	
	public SectorType[] getSectors() {
		return sectors;
	}
	public void setSectors(SectorType[] sectors) {
		this.sectors = sectors;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<SectorType> getCorrSectorTypeList() {
		return corrSectorTypeList;
	}


	public void setCorrSectorTypeList(List<SectorType> corrSectorTypeList) {
		this.corrSectorTypeList = corrSectorTypeList;
	}
	
	

}
