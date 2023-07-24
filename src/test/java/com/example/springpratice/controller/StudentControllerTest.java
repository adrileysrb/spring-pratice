package com.example.springpratice.controller;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.springpratice.dto.StudentDTO;
import com.example.springpratice.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    @Test
    void getAll() throws Exception{
        List<StudentDTO> students = Arrays.asList(
            new StudentDTO(1L, "João"), 
            new StudentDTO(2L, "Maria"), 
            new StudentDTO(3L, "Pedro"), 
            new StudentDTO(4L, "Ana"), 
            new StudentDTO(5L, "Carlos"), 
            new StudentDTO(6L, "Adriley"), 
            new StudentDTO(7L, "Samuel")
        );

        when(service.getAllStudents()).thenReturn(students);

        String studentsJson = new ObjectMapper().writeValueAsString(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(studentsJson));
    }

    @Test
    void getById() {
    }

    @Test
    void post() {
    }

    @Test
    void put() {
    }

    @Test
    void delete() {
    }
}