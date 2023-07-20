package com.codeurmas.sectors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeurmas.sectors.model.Person;
import com.codeurmas.sectors.model.PersonInSector;

public interface PersonInSectorRepository extends JpaRepository<PersonInSector, Long> {

}
