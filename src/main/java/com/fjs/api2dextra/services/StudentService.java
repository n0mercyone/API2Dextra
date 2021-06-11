package com.fjs.api2dextra.services;

import java.util.ArrayList;
import java.util.List;

import com.fjs.api2dextra.dto.BadRequest;
import com.fjs.api2dextra.dto.StudentDTO;
import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.model.Student;
import com.fjs.api2dextra.repository.IStudentRepository;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private IStudentRepository repository;

    public StudentService(IStudentRepository studentRepository) {
        this.repository = studentRepository;
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public List<Student> findCharactersByHouse(String houseID) {
        return repository.findCharactersByHouse(houseID);
    }

    public BadRequest validateCharacter(StudentDTO value) {
        BadRequest rq = new BadRequest();
        List<String> erros = new ArrayList<String>();
        if (value.getHouse() == null) {
            rq.setCode(400);
            erros.add("O campo house é obrigatório.");
        }

        if (value.getName() == null) {
            rq.setCode(400);
            erros.add("O campo name é obrigatório.");
        }

        if (value.getRole() == null) {
            rq.setCode(400);
            erros.add("O campo role é obrigatório.");
        }

        rq.setMessage(erros);
        return rq;
    }

    public Student save(Student student) throws Exception{
        Student newStudent;
        if(student.isValid()){/** validar se os campos necessários estão ok para a persistencia */
            newStudent = repository.save(student);
        }else{
            throw new Exception("Erro ao persistir o personagem, verifique os campos obrigatórios.");
        }
        return newStudent;
    }

    /**
     * 
     * @param hs      = Casa que o personagem
     * @param student = class no padrão "dto" do personagem
     * @return Um objeto Student
     */
    public Student createStudent(House hs, StudentDTO student) {
        Student sd = new Student();
        sd.setName(student.getName());
        sd.setRole(student.getRole());
        sd.setPatronus(student.getPatronus());
        sd.setHouse(hs);
        return sd;
    }

}
