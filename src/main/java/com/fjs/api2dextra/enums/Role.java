package com.fjs.api2dextra.enums;

public enum Role {
    
    STUDENT("student"), TEACHER("teacher");
    private final String role;

    private Role(String value) {
        role = value;
    }

    public String getValue(){
        return role;
    }

}
