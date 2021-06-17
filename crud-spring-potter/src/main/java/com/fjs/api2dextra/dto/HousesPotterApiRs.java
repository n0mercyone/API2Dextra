package com.fjs.api2dextra.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
@Data
public class HousesPotterApiRs {
    public List<HouseRs> houses;
}
