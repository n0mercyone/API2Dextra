package com.fjs.api2dextra.services.definition;

import java.util.List;

import com.fjs.api2dextra.model.House;

public interface IHouseService {

    public List<House> findAll();
    public House findById(String id);
    public House save(House value);
}
