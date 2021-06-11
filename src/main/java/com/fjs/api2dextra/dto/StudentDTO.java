package com.fjs.api2dextra.dto;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@XmlRootElement
@Data
@ApiModel(value = "character")
public class StudentDTO {

    @ApiModelProperty(value = "nome do personagem.")
    public String name;
    @ApiModelProperty(value = "define a regra do personagem, ex: student, teacher, etc.")
    public String role;
    @ApiModelProperty(value = "nome da escola do personagem")
    public String school;
    @ApiModelProperty(value = "código da casa que o personagem será associado.")
    public String house;
    @ApiModelProperty(value = "código da casa que o personagem será associado.")
    public String patronus;
    
}
