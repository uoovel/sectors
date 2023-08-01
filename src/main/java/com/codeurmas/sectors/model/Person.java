package com.codeurmas.sectors.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

@Entity
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column
	@Length(min=1, max=100, message="{Check name}")
	private String Name;

	
	
	
	public Person() {
		
	}
	

	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}


	@Override
	public String toString() {
		return "Person [Id=" + Id + ", Name=" + Name + "]";
	}
	
	

}
