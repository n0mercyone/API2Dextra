package com.fjs.api2dextra.services.implementation;

import java.util.List;

import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.repository.IHouseRepository;
import com.fjs.api2dextra.services.definition.IHouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService implements IHouseService{
    
    @Autowired
    private IHouseRepository repository;

    @Override
    public List<House> findAll() {        
        return repository.findAll();
    }

    @Override
    public House findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public House save(House value) {        
        return repository.save(value);
    }
    
}
