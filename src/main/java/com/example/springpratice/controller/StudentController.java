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
    public StudentEntity getById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentEntity post(@RequestBody @Valid StudentEntity studentEntity){
        return service.createStudent(studentEntity);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public StudentEntity put(@PathVariable Long id, @RequestBody @Valid StudentEntity studentEntity){
        return service.updateStudent(id, studentEntity);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.deleteStudent(id);
    }
}
