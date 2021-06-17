package com.fjs.api2dextra.dto;

import javax.xml.bind.annotation.XmlRootElement;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@XmlRootElement
@Data
@ApiModel(value = "teacher")
public class TeacherDTO {

    public String name;
    public String role;
    
}
