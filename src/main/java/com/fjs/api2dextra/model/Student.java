package com.fjs.api2dextra.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
    private House house;

    public void study(){}
    
}
