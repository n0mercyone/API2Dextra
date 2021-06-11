package com.fjs.api2dextra.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fjs.api2dextra.dto.HouseDTO;
import com.vladmihalcea.hibernate.type.array.ListArrayType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.rest.core.annotation.RestResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "houses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
public class House implements Serializable{

    @Id
    public String id;

    @OneToOne
    @JoinColumn(name = "header_id", referencedColumnName = "id")
    @RestResource(path = "houseTeatchers", rel="members")    
    public Teacher headOfHouse;
    
    public String mascot;
    public String houseGhost;
    public String name;
    @Column(name="created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    public String school;
    @Type(type = "list-array")    
    @Column(name = "colors", columnDefinition = "text[]")
    public List<String> colors;
    public String founder;
    @Type(type = "list-array")    
    @Column(name= "values",columnDefinition = "text[]")
    public List<String> values;
    

    public static House converter(HouseDTO value){
        House house = new House();
        house.setId(value.getId());
        house.setName(value.getName());
        house.setMascot(value.getMascot());
        house.setColors(value.getColors());
        house.setValues(value.getValues());
        house.setHouseGhost(value.getHouseGhost());
        house.setSchool(value.getSchool());
        house.setFounder(value.getFounder());
        return house;
    }
}
