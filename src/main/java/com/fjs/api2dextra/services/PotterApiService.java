package com.fjs.api2dextra.services;

import java.util.ArrayList;
import java.util.List;

import com.fjs.api2dextra.dto.TeacherDTO;
import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.repository.IPotterApiRepository;

import org.springframework.stereotype.Service;

@Service
public class PotterApiService {

    private IPotterApiRepository repository;
    

    /**
     * Dependecy Inversion
     * 
     * @param alguma interface que implemente IPotterApiRepository
     */
    public PotterApiService(IPotterApiRepository repository) {
        this.repository = repository;
    }

     /**
     * Recupera as casas via PotterApi
     * 
     * @return Lista de casas
     */
    public List<House> getHouses() {
        List<House> list = new ArrayList<House>();
        list = repository.getHouses();
        return list;
    }

    public TeacherDTO getHeadOfHouse(String houseId) {
        return repository.getHeadOfHouse(houseId);
    }


}
