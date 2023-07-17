package com.codeurmas.sectors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeurmas.sectors.model.Person;
import com.codeurmas.sectors.repository.PersonRepository;

@Service
@Transactional
public class PersonService {
	
	@Autowired
	private PersonRepository repo;

	public void save(Person person) {
		repo.save(person);
		
	}

}
