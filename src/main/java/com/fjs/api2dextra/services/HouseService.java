package com.fjs.api2dextra.services;

import java.util.List;

import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.repository.IHouseRepository;
import com.fjs.api2dextra.services.exceptions.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {
    
    @Autowired
    private IHouseRepository repository;

    public HouseService(){
    }

    public List<House> findAll(){                       
        return repository.findAll();
    }

    public House findById(String id){               
        House hs = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Nenhuma casa foi encontrada para o id: "+id));
        return hs;
    }

    public House save(House value){
        return repository.save(value);
    }
    
}
