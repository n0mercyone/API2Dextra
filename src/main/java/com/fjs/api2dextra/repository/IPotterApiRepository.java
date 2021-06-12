package com.fjs.api2dextra.repository;

import java.util.List;

import com.fjs.api2dextra.dto.HousesPotterApiRs;
import com.fjs.api2dextra.dto.TeacherDTO;
import com.fjs.api2dextra.model.House;

import org.springframework.stereotype.Repository;

@Repository
public interface IPotterApiRepository {

    public List<House> getHousesFromPotterApi();
    public TeacherDTO getHeadOfHouse(String houseId);
    public HousesPotterApiRs getHousesFromPotterApiRs();
    
}
