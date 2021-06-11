package com.fjs.api2dextra.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student extends Member{

    @Getter @Setter
    private String patronus;
    
    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    @RestResource(path = "houseStudents", rel="houses")  
    @NotNull
    private House house;

    public void study(){}

    @Transient
    @JsonIgnore
    public boolean isValid(){
        return getName() != null && getHouse() != null && getRole() != null;
    }
    
}
