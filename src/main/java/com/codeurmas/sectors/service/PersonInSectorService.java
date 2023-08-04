package com.codeurmas.sectors.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeurmas.sectors.model.Person;
import com.codeurmas.sectors.model.PersonInSector;
import com.codeurmas.sectors.model.SectorType;
import com.codeurmas.sectors.repository.PersonInSectorRepository;
import com.codeurmas.sectors.repository.PersonRepository;
import com.codeurmas.sectors.service.dto.FrontDto;

@Service
@Transactional
public class PersonInSectorService {
	@Autowired
	private PersonInSectorRepository repo;
	//@Autowired
	//private PersonRepository repoPerson;

	public PersonInSector save(PersonInSector personInSector) {
		return repo.save(personInSector);		
	}

	public PersonInSector edit(PersonInSector personInSector) {
		// TODO Auto-generated method stub
		return repo.save(personInSector);
	}

	public void deleteByPerson(Person person) {
		repo.deleteByPerson(person);
		
	}
    /*
	public FrontDto confirmSession(FrontDto frontDto, List<SectorType> corrSectorTypeList, String action) {
		
				return frontDto;
				//confirm session
	}*/
}
