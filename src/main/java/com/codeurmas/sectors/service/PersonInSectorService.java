package com.codeurmas.sectors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeurmas.sectors.model.PersonInSector;
import com.codeurmas.sectors.repository.PersonInSectorRepository;

@Service
@Transactional
public class PersonInSectorService {
	@Autowired
	private PersonInSectorRepository repo;

	public PersonInSector save(PersonInSector personInSector) {
		return repo.save(personInSector);		
	}
}
