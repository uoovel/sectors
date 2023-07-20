package com.codeurmas.sectors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeurmas.sectors.model.SectorType;
import com.codeurmas.sectors.repository.SectorTypeRepository;

@Service
@Transactional
public class SectorTypeService {
	
	@Autowired
	private SectorTypeRepository repo;

	public SectorType save(SectorType sectorType) {
		return repo.save(sectorType);
		
	}

	public List findAll() {
		
		return repo.findAll();
	}

}
