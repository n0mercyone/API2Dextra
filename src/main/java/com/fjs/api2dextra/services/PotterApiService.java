package com.fjs.api2dextra.services;

import java.util.ArrayList;
import java.util.List;

import com.fjs.api2dextra.dto.HousesPotterApiRs;
import com.fjs.api2dextra.dto.TeacherDTO;
import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.repository.IPotterApiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PotterApiService {

    @Autowired
    private IPotterApiRepository repository;
    

    /**
     * Dependecy Inversion
     * 
     * @param repository qualquer interface que implemente IPotterApiRepository
     */
    public PotterApiService(IPotterApiRepository repository) {
        this.repository = repository;
    }

     /**
     * Recupera as casas via PotterApi
     * 
     * @return Lista de casas
     */
    public List<House> getHousesFromPotterApi() {
        List<House> list = new ArrayList<House>();        
        list = repository.getHousesFromPotterApi();
        return list;
    }

         /**
     * Recupera as casas via PotterApi
     * 
     * @return Lista de casas
     */
    public HousesPotterApiRs getHousesFromPotterApiRs() {
        return repository.getHousesFromPotterApiRs();
    }

    public TeacherDTO getHeadOfHouse(String houseId) {
        return repository.getHeadOfHouse(houseId);
    }


}
