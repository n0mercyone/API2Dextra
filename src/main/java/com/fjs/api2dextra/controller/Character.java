package com.fjs.api2dextra.controller;

import java.util.ArrayList;
import java.util.List;

import com.fjs.api2dextra.dto.CustomResponse;
import com.fjs.api2dextra.dto.StudentRq;
import com.fjs.api2dextra.dto.StudentRs;
import com.fjs.api2dextra.model.Student;
import com.fjs.api2dextra.services.HouseService;
import com.fjs.api2dextra.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "*")
@Api(value = "Characters", description = "API REST para os personagens", tags = { "Characters" })
public class Character {

    @Autowired
    private StudentService studentService;

    @Autowired
    private HouseService houseService;

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista com todos os personagens."),
            @ApiResponse(code = 403, message = "Você não tem permissão."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção.") })
    @RequestMapping(path = "/", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna todos os personagens cadastrados.", tags = { "Characters" })
    public ResponseEntity<List<StudentRs>> getAll() {
        try {
            List<StudentRs> students = new ArrayList<StudentRs>();
            /** obter todos os personagens */
            studentService.findAllStudentRs().forEach(students::add);

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um personagen."),
            @ApiResponse(code = 403, message = "Você não tem permissão."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção.") })
    @RequestMapping(path = "/{id}", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna um personagen filtrando pelo seu id.", tags = { "Characters" })
    public ResponseEntity<StudentRs> getCharacter(@PathVariable("id") Integer id) {

        StudentRs student = new StudentRs();

        /** obter personagem filtrando pelo id */
        student = StudentRs.converter(studentService.findById(id));
        return ResponseEntity.ok(student);

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista com todos os personagens filtrados pelo id da casa."),
            @ApiResponse(code = 403, message = "Você não tem permissão."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção.") })
    @RequestMapping(path = "/house/{id}", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna todos os personagens cadastrados filtrando por sua respectiva casa.", tags = {
            "Characters" })
    public ResponseEntity<List<StudentRs>> getCharactersByHouse(@PathVariable("id") String id) {
        try {
            List<StudentRs> students = new ArrayList<StudentRs>();

            /** obter personagem por sua respectiva casa */
            students = studentService.findCharactersByHouse(id);

            if (students.size() == 0)
                return ResponseEntity.noContent().build();

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Cadastra um personagem nos registros."),
            @ApiResponse(code = 403, message = "Você não tem permissão."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção.") })
    @RequestMapping(path = "/", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "Cadastro de personagens.", tags = { "Characters" })
    public ResponseEntity<?> save(@RequestBody StudentRq studentRq) throws Exception {
        CustomResponse cr = studentService.validateCharacter(studentRq);

        if (cr.getMessage().size() > 0)
            return ResponseEntity.badRequest().body(cr);

        studentService.save(studentRq);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Exclui um personagem dos registros."),
            @ApiResponse(code = 403, message = "Você não tem permissão."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção.") })
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Exclusão de personagens.", tags = { "Characters" })
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {

        boolean hasDelected = studentService.delete(studentService.findById(id));

        if (hasDelected)/** statusCode = 204 para exclusão sem retorno no response */
            return ResponseEntity.noContent().build();
        else /** statusCode = 404 para informar que não encontrou o registro para exclusão */
            return ResponseEntity.notFound().build();

    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Atualiza o registro do personagem."),
            @ApiResponse(code = 403, message = "Você não tem permissão."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção.") })
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Atualização de personagens.", tags = { "Characters" })
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody StudentRq student) {

        CustomResponse cr = studentService.validateCharacter(student);

        if (cr.getMessage().size() > 0)
            return ResponseEntity.badRequest().body(cr);

        Student st = new Student();
        st = studentService.findById(id);
        st.setName(student.getName());
        st.setRole(student.getRole());
        st.setPatronus(student.getPatronus());
        st.setHouse(houseService.findById(student.getHouse()));
        
        studentService.update(st);
        return ResponseEntity.ok().build();

    }
}