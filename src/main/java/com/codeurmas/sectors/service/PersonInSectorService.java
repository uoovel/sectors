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
	@Autowired
	private PersonRepository repoPerson;

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


	public @Valid FrontDto saveSelections(FrontDto frontDto, String action) {
		//confirm session
        SectorType[] sectorTypes = frontDto.getSectors();		
		Person person = new Person();		
		person.setName(frontDto.getName());
		Person personSaved = new Person();
		Person personEdited = new Person();
		if(action.matches("Save")) {
			personSaved = repoPerson.save(person);
		}
		if(action.matches("Edit")) {
			Long personId = frontDto.getPersonId();
			person.setId(personId);
			personEdited = repoPerson.save(person);
			//Clear previously selected sections
			repo.deleteByPerson(person);
		}
		for(int i = 0; i < sectorTypes.length; i++) {
			PersonInSector personInSector = new PersonInSector();
			personInSector.setSectorType(sectorTypes[i]);
			personInSector.setAgreeTerms(true);
			Long personId = null;
			Long personInSectorId = null;
			if(action.matches("Save")) {
				personInSector.setPerson(personSaved);				
				PersonInSector personInSectorSaved = repo.save(personInSector);
				personId = personSaved.getId();
				personInSectorId = personInSectorSaved.getId();
				
			}
			if(action.matches("Edit")) {
				personInSector.setPerson(personEdited);
				PersonInSector personInSectorEdited = repo.save(personInSector);
				personId = personEdited.getId();
				personInSectorId = personInSectorEdited.getId();
			}
			
			frontDto.setPersonId(personId);
			frontDto.setPersonInSectorId(personInSectorId);
	
		}//for
		return frontDto;
		
	}
}
