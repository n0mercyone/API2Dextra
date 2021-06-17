package com.fjs.api2dextra.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import com.fjs.api2dextra.dto.StudentRq;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
// @WebMvcTest(controllers = Character.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CharacterTest {

    @Autowired
    private MockMvc mockito;

    /** teste para obter todos os characteres (statusCode = 200) */
    @Test
    public void shouldReturnAllCharacter() throws Exception {
        mockito.perform(get("/api/characters/")).andExpect(status().isOk());

    }

    /** teste para obter todos os personagens (statusCode = 200) */
    @Test
    public void shouldReturnOneCharacter() throws Exception {
        mockito.perform(get("/api/characters/{id}", 26)).andExpect(status().isOk());

    }

    /** teste de personagem não encontrado (statusCode = 404) */
    @Test
    public void shouldReturnNotFoundCharacter() throws Exception {
        mockito.perform(
                get("/api/characters/{id}", 99).content(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /** teste de cadastro de personagem (statusCode = 200) */
    @Test
    public void whenCreateCharacter_GettingSuccess() throws Exception {

        StudentRq studentRq = new StudentRq();
        studentRq.setName("Frank");
        studentRq.setRole("student");
        studentRq.setPatronus("stag");
        studentRq.setHouse("1760529f-6d51-4cb1-bcb1-25087fce5bde");

        mockito.perform(post("/api/characters/", studentRq).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(studentRq)))
                .andExpect(status().isOk());
    }

}
