package com.fjs.api2dextra.services.definition;

import java.util.List;

import com.fjs.api2dextra.dto.HousesPotterApiRs;
import com.fjs.api2dextra.dto.TeacherDTO;
import com.fjs.api2dextra.model.House;

public interface IPotterApiService {

    public List<House> getHousesFromPotterApi();
    public HousesPotterApiRs getHousesFromPotterApiRs();
    public TeacherDTO getHeadOfHouse(String houseId);
    
}
