package com.fjs.api2dextra.services.implementation;

import com.fjs.api2dextra.model.Teacher;
import com.fjs.api2dextra.repository.ITeacherRepository;
import com.fjs.api2dextra.services.definition.ITeacherService;

import org.springframework.stereotype.Service;

@Service
public class TeacherService implements ITeacherService{


    private ITeacherRepository repository;

    public TeacherService(ITeacherRepository repository){
        this.repository = repository;
    }

    @Override
    public Teacher saveTeacher(Teacher teacher){
        return repository.save(teacher);
    }

    
}
