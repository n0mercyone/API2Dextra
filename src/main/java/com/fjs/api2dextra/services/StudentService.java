package com.fjs.api2dextra.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fjs.api2dextra.dto.CustomResponse;
import com.fjs.api2dextra.dto.StudentRq;
import com.fjs.api2dextra.dto.StudentRs;
import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.model.Student;
import com.fjs.api2dextra.repository.IStudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private IStudentRepository repository;

    private HouseService houseService;
    private PotterApiService potterApiService;

    public StudentService(HouseService houseService, PotterApiService potterApiService) {
        this.houseService = houseService;
        this.potterApiService = potterApiService;
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public List<StudentRs> findAllStudentRs() {
        return repository.findAll().stream().map(StudentRs::converter).collect(Collectors.toList());
    }

    public Student findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(Student student) {
        boolean hasDone = false;
        try {
            repository.delete(student);
            hasDone = true;
        } catch (Exception e) {

        }
        return hasDone;

    }

    public List<StudentRs> findCharactersByHouse(String houseID) {
        List<Student> listStudents = repository.findCharactersByHouse(houseID);
        return listStudents.stream().map(StudentRs::converter).collect(Collectors.toList());
    }

    public CustomResponse validateCharacter(StudentRs value) {
        CustomResponse rq = new CustomResponse();
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

    public Student save(StudentRq studentRq) {
        Student student = null;
        House house = obtainHouse(studentRq.getHouse());
        
        if (!Objects.isNull(house)) {            
            student = new Student();            
            student.setHouse(house);
            student.setName(studentRq.getName());
            student.setRole(studentRq.getRole());
            student.setPatronus(studentRq.getPatronus());
            repository.save(student);
        }

        return student;
    }

    public Student update(Student student) {
        repository.save(student);
        return student;
    }

    /**
     * 
     * @param hs      = Casa que o personagem
     * @param student = class no padrão "dto" do personagem
     * @return Um objeto Student
     */
    public Student createStudent(House hs, Student student) {
        Student sd = new Student();
        sd.setName(student.getName());
        sd.setRole(student.getRole());
        sd.setPatronus(student.getPatronus());
        sd.setHouse(hs);
        return sd;
    }

    public House obtainHouse(String id) {
        House house = houseService.findById(id);
        List<House> list;
        /**
         * caso não encontre a casa no banco de dados da aplicação, obter via potterApi
         */
        if (house == null) {
            list = potterApiService.getHousesFromPotterApi().stream().filter(hs -> hs.getId().equalsIgnoreCase(id))
                    .collect(Collectors.toList());
            house = list.size() > 0 ? list.get(0) : null;
        }
        return house;
    }

}
