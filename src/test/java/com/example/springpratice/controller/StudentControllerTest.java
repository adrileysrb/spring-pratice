package com.example.springpratice.controller;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
    void getById() throws Exception {
        Long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO(studentId, "Joao");
        when(service.getStudentById(studentId)).thenReturn(studentDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/{id}", studentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"name\":\"Joao\",\"id\":1}"));
    }

    @Test
    void post() throws Exception {
        StudentDTO studentDTOToCreate = new StudentDTO(null, "Joao");
        StudentDTO studentDTOCreated = new StudentDTO(1L, "Joao");
        when(service.createStudent(studentDTOToCreate)).thenReturn(studentDTOCreated);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Joao\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"name\":\"Joao\",\"id\":1}"));
    }

    @Test
    void put() throws Exception{
        Long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO(studentId, "Joao");
        when(service.updateStudent(eq(studentId), any(StudentDTO.class))).thenReturn(studentDTO);

        String studentDTOJson = new ObjectMapper().writeValueAsString(studentDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/student/{id}", studentId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(studentDTOJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Joao"));
    }

    @Test
    void delete() throws Exception{
        Long studentId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/{id}", studentId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verify times when service.deleteStudent was called when get a exist studentId
        verify(service, times(1)).deleteStudent(studentId);
    }

}