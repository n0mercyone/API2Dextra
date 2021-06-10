package com.fjs.api2dextra.dto;

import java.util.List;

import lombok.Data;

@Data
public class BadRequest {

    private int code;
    private List<String> message;
    
}
