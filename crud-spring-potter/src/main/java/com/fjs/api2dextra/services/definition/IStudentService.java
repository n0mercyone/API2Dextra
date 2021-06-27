package com.fjs.api2dextra.services.definition;

import java.util.List;

import com.fjs.api2dextra.dto.StudentRq;
import com.fjs.api2dextra.dto.StudentRs;
import com.fjs.api2dextra.model.Student;

public interface IStudentService {

    public Student save(StudentRq studentRq);
    public Student update(Student student);
    public List<Student> findAll();
    public List<StudentRs> findAllStudentRs();
    public Student findById(Integer id);
    public boolean delete(Student student);
    public List<StudentRs> findCharactersByHouse(String houseID);
    
    
}
