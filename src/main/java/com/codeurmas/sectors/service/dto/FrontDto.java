package com.codeurmas.sectors.service.dto;

import com.codeurmas.sectors.model.Person;
import com.codeurmas.sectors.model.SectorType;

public class FrontDto {
	
	private Person person;
	private SectorType[] sectors;
	
	
	public FrontDto() {
		
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public SectorType[] getSectors() {
		return sectors;
	}
	public void setSectors(SectorType[] sectors) {
		this.sectors = sectors;
	}
	

}
