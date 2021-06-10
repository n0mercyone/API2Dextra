package com.fjs.api2dextra.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fjs.api2dextra.dto.BadRequest;
import com.fjs.api2dextra.dto.StudentDTO;
import com.fjs.api2dextra.model.House;
import com.fjs.api2dextra.model.Student;
import com.fjs.api2dextra.repository.HouseRepository;
import com.fjs.api2dextra.repository.PotterApiRepositoryJersey;
import com.fjs.api2dextra.repository.StudentRepository;
import com.google.gson.Gson;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CharacterController {
    private StudentRepository studentRepository;
    private HouseRepository houseRepository;
    private PotterApiRepositoryJersey potterApiRepositoryJersey;

    CharacterController(StudentRepository repository, HouseRepository hRepository,
            PotterApiRepositoryJersey potterApi) {
        studentRepository = repository;
        houseRepository = hRepository;
        potterApiRepositoryJersey = potterApi;
    }

    @GetMapping(path = "/characters")
    public ResponseEntity<List<Student>> getAll() {
        try {
            List<Student> students = new ArrayList<Student>();

            studentRepository.findAll().forEach(students::add);

            if (students.isEmpty())
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/characters/house/{id}")
    public ResponseEntity<Student> getCharactersByHouse(@PathVariable("id") String id) {
        try {
            Student student;

            student = studentRepository.findCharactersByHouse(id);

            if (student == null)
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<StudentDTO> save2(@RequestBody StudentDTO student) throws Exception {

        return ResponseEntity.ok(student);
    }

    @PostMapping(path = "/characters", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> save(@RequestBody StudentDTO student) throws Exception {
        Student sd = new Student();
        House house = null;
        List<House> houses;
        Gson gson = new Gson();
        BadRequest br = validateCharacter(student);

        if (br.getMessage().size() > 0) 
            return ResponseEntity.badRequest().body(gson.toJson(br));
        

        if (!student.getHouse().trim().isEmpty()) {
            if (houseRepository.findById(student.getHouse()).isPresent())
                house = houseRepository.findById(student.getHouse()).get();
            /**
             * CHECK HOUSE ON DATABASE, ELSE GET FROM PoterApi
             */
            if (house == null) {
                houses = taskHousesAPI();

                if (houses.size() > 0) {

                    houses = houses.stream().filter(hs -> hs.getId().equalsIgnoreCase(student.getHouse()))
                            .collect(Collectors.toList());

                    House persistHouse = houses.get(0);
                    houseRepository.save(persistHouse);
                    sd = createStudent(persistHouse, student);
                    studentRepository.save(sd);
                } else {
                    return ResponseEntity.ok(gson.toJson(sd));
                }

            } else {
                sd = createStudent(house, student);
                studentRepository.save(sd);
            }

        }
        return ResponseEntity.ok(gson.toJson(sd));

    }

    BadRequest validateCharacter(StudentDTO value) {
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

    /**
     * 
     * @param hs      = Casa que o personagem
     * @param student = class no padrão "dto" do personagem
     * @return
     */
    public Student createStudent(House hs, StudentDTO student) {
        Student sd = new Student();
        sd.setName(student.getName());
        sd.setRole(student.getRole());
        sd.setPatronus(student.getPatronus());
        sd.setHouse(hs);
        return sd;
    }

    public void createTeacher(House hs) {

    }

    /**
     * Recupera as casas via PotterApi
     * 
     * @return Lista de casas
     */
    public List<House> taskHousesAPI() {
        List<House> houses = potterApiRepositoryJersey.getHouses();
        return houses;
    }

}
