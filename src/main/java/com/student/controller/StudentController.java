package com.student.controller;

import com.student.dto.ApiResponse;
import com.student.dto.StudentResponseDto;
import com.student.params.StudentCreateRequestParams;
import com.student.params.StudentUpdateRequestParams;
import com.student.service.StudentService;
import com.student.utils.url.UrlStudentPaths;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(UrlStudentPaths.BASE)
public class StudentController extends BaseController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> saveStudent(@Valid @RequestBody StudentCreateRequestParams params) {
        ApiResponse<StudentResponseDto> response = studentService.saveStudent(params);
        return buildResponse(response);
    }

    @GetMapping(UrlStudentPaths.STUDENT_LIST)
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getAllStudents() {
        ApiResponse<List<StudentResponseDto>> response = studentService.getAllStudents();
        return buildResponse(response);
    }

    @GetMapping(UrlStudentPaths.BY_ID)
    public ResponseEntity<ApiResponse<StudentResponseDto>> getStudentById(@PathVariable UUID id) {
        ApiResponse<StudentResponseDto> response = studentService.getStudentById(id);
        return buildResponse(response);
    }

    @PutMapping(UrlStudentPaths.UPDATE)
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(@Valid @PathVariable UUID id, @RequestBody StudentUpdateRequestParams params) {
        ApiResponse<StudentResponseDto> response = studentService.updateStudent(id, params);
        return buildResponse(response);
    }

    @DeleteMapping(UrlStudentPaths.DELETE)
    public ResponseEntity<ApiResponse<StudentResponseDto>> deleteStudent(@PathVariable UUID id) {
        ApiResponse<StudentResponseDto> response = studentService.deleteStudent(id);
        return buildResponse(response);
    }
}