package com.fjs.api2dextra.services;

import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.repository.IHouseRepository;

import org.springframework.stereotype.Service;

@Service
public class HouseService {
    
    private IHouseRepository repository;

    public HouseService(IHouseRepository repository){
        this.repository = repository;
    }

    public House findById(String id){               
        House hs = repository.findById(id).isPresent() ? repository.findById(id).get() : null;
        return hs;
    }

    public void save(House value){
        repository.save(value);
    }
    
}
