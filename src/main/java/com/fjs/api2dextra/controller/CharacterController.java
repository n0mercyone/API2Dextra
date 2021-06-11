package com.fjs.api2dextra.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fjs.api2dextra.dto.BadRequest;
import com.fjs.api2dextra.dto.StudentDTO;
import com.fjs.api2dextra.dto.TeacherDTO;
import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.model.Student;
import com.fjs.api2dextra.model.Teacher;
import com.fjs.api2dextra.repository.IHouseRepository;
import com.fjs.api2dextra.repository.IPotterApiRepository;
import com.fjs.api2dextra.repository.IStudentRepository;
import com.fjs.api2dextra.repository.ITeacherRepository;
import com.fjs.api2dextra.services.HouseService;
import com.fjs.api2dextra.services.PotterApiService;
import com.fjs.api2dextra.services.StudentService;
import com.fjs.api2dextra.services.TeacherService;
import com.fjs.api2dextra.util.Util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Api(value = "api2dextra")
public class CharacterController {

    private StudentService studentService;
    private HouseService houseService;
    private PotterApiService potterService;
    private TeacherService teacherService;
    private Student newStudent = null;
    private House house = null;
    private Teacher teacher = null;

    CharacterController(IStudentRepository studentRepository, IHouseRepository houseRepository,
            IPotterApiRepository potterApiRepository, ITeacherRepository teacherRepository) {
        studentService = new StudentService(studentRepository);
        houseService = new HouseService(houseRepository);
        potterService = new PotterApiService(potterApiRepository);
        teacherService = new TeacherService(teacherRepository);
    }

    @GetMapping(path = "/characters")
    @ApiOperation(value = "Retorna todos os personagens cadastrados.")
    public ResponseEntity<List<Student>> getAll() {
        try {
            List<Student> students = new ArrayList<Student>();

            /**
             * obter todos os personagens
             */
            studentService.findAll().forEach(students::add);

            /**
             * caso não haja registro na lista, um array vazio será exibido
             */
            if (students.isEmpty())
                return ResponseEntity.ok(students);

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/characters/house/{id}")
    @ApiOperation(value = "Retorna todos os personagens cadastrados filtrando pelo id da casa.")
    public ResponseEntity<List<Student>> getCharactersByHouse(@PathVariable("id") String id) {
        try {
            List<Student> students = new ArrayList<Student>();
            /**
             * obter personagem por sua respectiva casa
             */
            students = studentService.findCharactersByHouse(id);

            if (students.size() > 0)
                return ResponseEntity.ok().body(students);

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/characters", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Cadastro um personagem na base de dados.")
    public ResponseEntity<String> save(@RequestBody StudentDTO student) throws Exception {

        /** validar campos do objeto da requisição */
        BadRequest br = studentService.validateCharacter(student);

        if (br.getMessage().size() > 0) {/** caso algum campo esteja faltando, exibir um retorno */
            return ResponseEntity.badRequest().body(Util.reponsePrettyJSON(br));
        }

        try {
            /** persiste o personagem */
            newStudent = saveCharacter(student);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    Util.reponsePrettyJSON(new BadRequest(400, Arrays.asList("Erro ao persistir o personagem."))));
        }

        return ResponseEntity.ok().body(Util.reponsePrettyJSON(newStudent));

    }

    public Student saveCharacter(StudentDTO student) throws Exception {
        newStudent = new Student();
        /** busca a casa no banco de dados */
        house = houseService.findById(student.getHouse());

        /** caso encontre, persiste o personagem */
        if (house != null) {
            newStudent = studentService.createStudent(house, student);
            newStudent = studentService.save(newStudent);

        } else {/** caso não encontre, buscar a casa na api externa */
            List<House> houses = potterService.getHouses().stream()
                    .filter(hs -> hs.getId().equalsIgnoreCase(student.getHouse())).collect(Collectors.toList());
            teacher = new Teacher();

            if (houses.size() > 0) {/** persiste a casa no banco de dados */
                house = houses.get(0);
                TeacherDTO teacherDTO = potterService.getHeadOfHouse(house.getId());
                teacher.setName(teacherDTO.getName());
                teacher.setRole(teacherDTO.getRole());

                /** persiste o professor responsável */
                teacher = teacherService.saveTeacher(teacher);

                /** associa o professor com a casa */
                house.setHeadOfHouse(teacher);
                
                /** persiste a casa */
                houseService.save(house);
            }

            Student std = studentService.createStudent(house, student);
            newStudent = studentService.save(std);
        }

        return newStudent;

    }

}
