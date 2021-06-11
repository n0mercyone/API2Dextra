package com.fjs.api2dextra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fjs.api2dextra.dto.HouseDTO;
import com.fjs.api2dextra.dto.RootDTO;
import com.fjs.api2dextra.dto.TeacherDTO;
import com.fjs.api2dextra.enums.Role;
import com.fjs.api2dextra.model.House;

import org.springframework.stereotype.Repository;

@Repository
public class PotterApiRepositoryJersey implements IPotterApiRepository {

    private static final String END_POINT = "http://us-central1-rh-challenges.cloudfunctions.net/potterApi";

    private TeacherDTO teacherDTO;
    private HouseDTO houseDTO;
    private Client client;
    private WebTarget webTarget;
    private Invocation.Builder invocationBuilder;
    private List<House> list;

    public PotterApiRepositoryJersey() {
        client = ClientBuilder.newClient();
        webTarget = client.target(END_POINT);
    }

    @Override
    public List<House> getHouses() {

        invocationBuilder = webTarget.path("/houses").request(MediaType.APPLICATION_JSON).header("apikey",
                "adec1516-d00c-4d36-bf9a-1ba30788457d");
        RootDTO response = invocationBuilder.get(RootDTO.class);
        taskResponse(response);
        return list;
    }

    private void taskResponse(RootDTO value) {
        list = new ArrayList<House>();
        if (value.houses.size() > 0) {

            value.houses.forEach((dto) -> {
                list.add(House.converter(dto));
            });

        }
    }

    @Override
    public TeacherDTO getHeadOfHouse(String houseId) {
        teacherDTO = new TeacherDTO();
        invocationBuilder = webTarget.path("/houses").request(MediaType.APPLICATION_JSON).header("apikey",
                "adec1516-d00c-4d36-bf9a-1ba30788457d");
        RootDTO response = invocationBuilder.get(RootDTO.class);
        houseDTO = response.houses.stream().filter(house -> house.getId().equals(houseId)).collect(Collectors.toList()).get(0);
        teacherDTO.setName(houseDTO.getHeadOfHouse());
        teacherDTO.setRole(Role.TEACHER.getValue());
        return teacherDTO;
    }

}
