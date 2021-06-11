package com.fjs.api2dextra.repository;

import java.util.List;
import com.fjs.api2dextra.dto.TeacherDTO;
import com.fjs.api2dextra.model.House;

import org.springframework.stereotype.Repository;

@Repository
public interface IPotterApiRepository {

    public List<House> getHouses();
    public TeacherDTO getHeadOfHouse(String houseId);
    
}
