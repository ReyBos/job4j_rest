package ru.reybos.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.reybos.auth.model.Person;
import ru.reybos.auth.repository.PersonRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PersonControllerModuleTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonRepository repository;

    @Test
    public void whenGetUserInfoByIdThenStatus200() throws Exception {
        Person person = Person.of("Login", "Password");
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(person));
        mockMvc.perform(
                get("/person/{id}", person.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("Login"))
                .andExpect(jsonPath("$.password").value("Password"));
    }

    @Test
    public void whenGetUserInfoByIdThenStatus404() throws Exception {
        Mockito.when(repository.findById(Mockito.anyInt())).
                thenReturn(Optional.empty());
        mockMvc.perform(
                get("/persons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteUserThenStatus200() throws Exception {
        Person person = Person.of("Login", "Password");
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(person));
        mockMvc.perform(
                delete("/person/{id}", person.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllPersonsThenStatus200() throws Exception {
        Person p1 = Person.of("Login1", "Password1");
        p1.setId(1);
        Person p2 = Person.of("Login2", "Password2");
        p2.setId(2);

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(
                get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(p1, p2))));
    }
}