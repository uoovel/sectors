package com.codeurmas.sectors.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PersonInSector {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "sectortype_id")
	private SectorType sectorType;
	
	@Column
	private boolean agreeTerms;
	
	public boolean isAgreeTerms() {
		return agreeTerms;
	}


	public void setAgreeTerms(boolean agreeTerms) {
		this.agreeTerms = agreeTerms;
	}


	public PersonInSector() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public SectorType getSectorType() {
		return sectorType;
	}


	public void setSectorType(SectorType sectorType) {
		this.sectorType = sectorType;
	}


	@Override
	public String toString() {
		return "PersonInSector [id=" + id + ", person=" + person + ", sectorType=" + sectorType + ", agreeTerms="
				+ agreeTerms + "]";
	}
	
	

}
