package com.example.springpratice.controller;

import com.example.springpratice.dto.StudentDTO;
import com.example.springpratice.entity.StudentEntity;
import com.example.springpratice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping
    public List<StudentDTO> getAll(){
        return service.getAllStudents();
    }

    @GetMapping("{id}")
    public StudentDTO getById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO post(@RequestBody @Valid StudentDTO studentDTO){
        return service.createStudent(studentDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public StudentDTO put(@PathVariable Long id, @RequestBody @Valid StudentDTO studentDTO){
        return service.updateStudent(id, studentDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.deleteStudent(id);
    }
}
