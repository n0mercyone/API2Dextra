package com.fjs.api2dextra.services.implementation;

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
import com.fjs.api2dextra.services.definition.IStudentService;
import com.fjs.api2dextra.services.exceptions.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements IStudentService{

    @Autowired
    private IStudentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private HouseService houseService;
    private PotterApiService potterApiService;

    /** contrutor recebendo houseService e potterApiService por injeção */
    public StudentService(HouseService houseService, PotterApiService potterApiService) {
        this.houseService = houseService;
        this.potterApiService = potterApiService;
    }

    /** retorna lista com todos os registros do tipo Student */
    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    /** retorna lista com todos os registros do tipo StudentRs */
    @Override
    public List<StudentRs> findAllStudentRs() {
        return repository.findAll().stream().map(StudentRs::converter).collect(Collectors.toList());
    }

    /** retorna um objeto do tipo Student */
    @Override
    public Student findById(Integer id) {
        /** lança uma exceção personalizada caso não encontre o registro. */
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado para o id " + id));
    }

    /** exclui um objeto Student da base de dados */
    @Override
    public boolean delete(Student student) {
        boolean hasDelected = false;
        try {
            repository.delete(student);
            hasDelected = true;
        } catch (Exception e) {

        }
        return hasDelected;

    }

    /** busca todos os personagens pelo id da casa */
    @Override
    public List<StudentRs> findCharactersByHouse(String houseID) {
        List<Student> listStudents = repository.findCharactersByHouse(houseID);
        return listStudents.stream().map(StudentRs::converter).collect(Collectors.toList());
    }

    /** método para validar os campos do personagem (Student) */
    public CustomResponse validateCharacter(StudentRq value) {
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

    /** cria um personagem na base de dados do tipo Student */
    @Override
    public Student save(StudentRq studentRq) {
        Student student = null;
        House house = obtainHouse(studentRq.getHouse());
        if (!Objects.isNull(house)) {
            student = new Student();
            student = modelMapper.map(studentRq, Student.class);
            student.setHouse(house);
            repository.save(student);   
        }

        return student;
    }

    @Override
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

    public StudentRq getStudentRq(StudentRs student){
        return this.modelMapper.map(student, StudentRq.class);
    }

    public StudentRs getStudentRs(Student student){
        return this.modelMapper.map(student, StudentRs.class);
    }

    /** recura a casa para ser utilizada no cadastro do personagem (Student) */
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
            if (Objects.isNull(house))
                throw new EntityNotFoundException("Não foi possível obter a casa pelo id: " + id);

            houseService.save(house);

        }

        return house;
    }

}
