package com.codeurmas.sectors.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SectorType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column
	private String typeName;
	
	@ManyToOne
	@JoinColumn(name = "granny_id")
	private SectorType granny;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private SectorType parent;
	
	public SectorType() {
		
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public SectorType getGranny() {
		return granny;
	}
	public void setGranny(SectorType granny) {
		this.granny = granny;
	}
	public SectorType getParent() {
		return parent;
	}
	public void setParent(SectorType parent) {
		this.parent = parent;
	}
	
	

}
