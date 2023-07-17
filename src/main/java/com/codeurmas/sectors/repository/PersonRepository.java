package com.codeurmas.sectors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeurmas.sectors.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
