package com.fjs.api2dextra.controller;

import com.fjs.api2dextra.dto.HousesPotterApiRs;
import com.fjs.api2dextra.services.implementation.PotterApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/houses")
@CrossOrigin(origins = "*")
@Api(value = "Houses", description = "API REST para as casas", tags = {"Houses"})
public class House {

    @Autowired
    private PotterApiService potterApiService;

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista das casas."),
            @ApiResponse(code = 403, message = "Você não tem permissão."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção.") })
    @RequestMapping(path = "/", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna a lista das casas disponíveis na PotterApi.", tags = {"Houses"})
    public ResponseEntity<HousesPotterApiRs> getAll() {
        try {
            HousesPotterApiRs houses = potterApiService.getHousesFromPotterApiRs();
            return ResponseEntity.ok().body(houses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
