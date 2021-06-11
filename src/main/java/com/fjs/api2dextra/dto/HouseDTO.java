package com.fjs.api2dextra.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
@Data
public class HouseDTO {
    
    public String id;
    public String headOfHouse;
    public String mascot;
    public String houseGhost;
    public String name;
    public String school;
    public List<String> colors;
    public String founder;
    public List<String> values;//1.320,36
    

}
