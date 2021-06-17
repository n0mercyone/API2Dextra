package com.fjs.api2dextra.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fjs.api2dextra.dto.StudentRq;
import com.fjs.api2dextra.model.Student;
import com.fjs.api2dextra.services.exceptions.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    /** teste de cadastro de um personagem no serviço */
    @Test
    public void whenSaveCharacter_GettingSuccess() {
        StudentRq studentRq = new StudentRq();
        studentRq.setName("Draco");
        studentRq.setRole("student");
        studentRq.setPatronus("stag");
        studentRq.setHouse("1760529f-6d51-4cb1-bcb1-25087fce5bde");
        Student student = studentService.save(studentRq);
        assertNotNull(student.getId());

    }

    /**
     * teste de cadastro de um personagem no serviço com o id da casa inexistente
     */
    @Test
    public void whenSaveCharacterWithWrongHouse_GettingThrow() {
        StudentRq studentRq = new StudentRq();
        studentRq.setName("Draco");
        studentRq.setRole("student");
        studentRq.setPatronus("stag");
        studentRq.setHouse("9999999999999999999999");
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.save(studentRq);
        });
    }

    /** teste de edição de um personagem no serviço */
    @Test
    public void whenUpdateCharacter_GettingSuccess() {
        Student student = studentService.findById(49);/** passar id pertinente ao personagem para ter sucesso. */
        student.setName("Draco Update");
        student.setRole("student");
        student.setPatronus("stag");
        studentService.update(student);
        assertEquals(student.getName(), "Draco Update");
    }


    /** teste de personagem encontrado no serviço */
    @Test
    public void whenGettingShouldReturnCharacter() {
        Student student = studentService.findById(26);/** passar id pertinente ao personagem para ter sucesso. */
        assertNotNull(student);

    }

    /** teste de personagem não encontrado no serviço */
    @Test
    public void whenExceptionThrown_GettingCharacterNotFound() {

        assertThrows(EntityNotFoundException.class, () -> {
            /**
             * id negativo para forçar a exception lançada no método findById dentro do
             * StudentService
             */
            studentService.findById(-1);
        });

    }

}
