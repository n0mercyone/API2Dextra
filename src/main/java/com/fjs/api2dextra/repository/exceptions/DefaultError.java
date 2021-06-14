package com.fjs.api2dextra.repository.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DefaultError implements Serializable{

    private static final long serialVersionUID = 1L;

    public Instant timestamp;
    public int status;
    public String error;
    public String message;
    public String path;
    
}
