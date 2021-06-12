package com.fjs.api2dextra.dto;

import javax.xml.bind.annotation.XmlRootElement;
import com.fjs.api2dextra.model.Student;
import lombok.Data;

@XmlRootElement
@Data
public class StudentRs {

    public Integer id;
    public String name;
    public String role;
    public String school;
    public String house;
    public String patronus;

    public static StudentRs converter(Student student){
        StudentRs rs = new StudentRs();
        rs.setId(student.getId());
        rs.setName(student.getName());
        rs.setRole(student.getRole());
        rs.setSchool(student.getHouse().getSchool());
        rs.setHouse(student.getHouse().getName());
        rs.setPatronus(student.getPatronus());
        return rs;
    }
    

}
