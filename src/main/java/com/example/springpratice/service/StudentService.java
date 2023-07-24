package com.example.springpratice.service;

import com.example.springpratice.dto.StudentDTO;
import com.example.springpratice.entity.StudentEntity;
import com.example.springpratice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentEntity createStudent(StudentEntity studentEntity){
        return studentRepository.save(studentEntity);
    }

    public List<StudentDTO> getAllStudents(){
        List<StudentEntity> students = studentRepository.findAll();
        return students.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public StudentEntity getStudentById(Long id){
        return studentRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public StudentEntity updateStudent(Long id, StudentEntity studentEntity){
        return studentRepository.findById(id)
                .map( student -> {
                    student.setName(studentEntity.getName());
                    return studentRepository.save(student);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public void deleteStudent(Long id){
        studentRepository.findById(id).map(student -> {
            studentRepository.delete(student);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    private StudentDTO mapToDTO(StudentEntity studentEntity){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentEntity.getId());
        studentDTO.setName(studentEntity.getName());
        return studentDTO;
    }
}
