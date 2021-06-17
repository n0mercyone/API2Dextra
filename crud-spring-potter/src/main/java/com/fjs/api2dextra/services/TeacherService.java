package com.fjs.api2dextra.services;

import com.fjs.api2dextra.model.Teacher;
import com.fjs.api2dextra.repository.ITeacherRepository;

import org.springframework.stereotype.Service;

@Service
public class TeacherService {


    private ITeacherRepository repository;

    public TeacherService(ITeacherRepository repository){
        this.repository = repository;
    }

    
    public Teacher saveTeacher(Teacher teacher){
        return repository.save(teacher);
    }

    
}
