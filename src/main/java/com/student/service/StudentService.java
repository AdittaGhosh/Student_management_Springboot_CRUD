package com.student.service;

import com.student.dto.ApiResponse;
import com.student.dto.StudentResponseDto;
import com.student.entity.Student;
import com.student.exception.DatabaseException;
import com.student.mapper.StudentMapper;
import com.student.params.StudentCreateRequestParams;
import com.student.params.StudentUpdateRequestParams;
import com.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public ApiResponse<StudentResponseDto> saveStudent(StudentCreateRequestParams params) {
        try {
            Student student = studentMapper.toEntity(params);
            student.setActive(true);
            student.setIsDeleted(0);
            Student savedStudent = studentRepository.save(student);
            if (savedStudent.getId() == null) {
                throw new DatabaseException("Student could not be saved");
            }
            StudentResponseDto responseDto = studentMapper.toResponseDto(savedStudent);
            return ApiResponse.<StudentResponseDto>builder()
                    .statusCode(200)
                    .message("Student Saved Successfully")
                    .data(responseDto)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<StudentResponseDto>builder()
                    .statusCode(500)
                    .message("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    public ApiResponse<List<StudentResponseDto>> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            List<StudentResponseDto> responseData = studentMapper.toResponseListDto(students).stream()
                    .filter(student -> student.getIsDeleted() == 0)
                    .collect(Collectors.toList());
            return ApiResponse.<List<StudentResponseDto>>builder()
                    .statusCode(200)
                    .message("All Students Retrieved Successfully")
                    .data(responseData)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<StudentResponseDto>>builder()
                    .statusCode(500)
                    .message("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    public ApiResponse<StudentResponseDto> getStudentById(UUID id) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                if (student.getIsDeleted() == 1) {
                    return ApiResponse.<StudentResponseDto>builder()
                            .statusCode(400)
                            .message("Student is deleted and cannot be retrieved")
                            .build();
                }
                StudentResponseDto responseData = studentMapper.toResponseDto(student);
                return ApiResponse.<StudentResponseDto>builder()
                        .statusCode(200)
                        .message("Student found")
                        .data(responseData)
                        .build();
            } else {
                return ApiResponse.<StudentResponseDto>builder()
                        .statusCode(404)
                        .message("Student not found")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.<StudentResponseDto>builder()
                    .statusCode(500)
                    .message("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    public ApiResponse<StudentResponseDto> updateStudent(UUID id, StudentUpdateRequestParams params) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isEmpty()) {
                return ApiResponse.<StudentResponseDto>builder()
                        .statusCode(404)
                        .message("Student not found")
                        .build();
            }
            Student studentToUpdate = studentOptional.get();
            if (studentToUpdate.getIsDeleted() == 1) {
                return ApiResponse.<StudentResponseDto>builder()
                        .statusCode(400)
                        .message("Student is deleted and cannot be updated")
                        .build();
            }
            studentToUpdate.setName(params.getName());
            studentToUpdate.setDob(params.getDob());
            studentToUpdate.setFatherName(params.getFatherName());
            studentToUpdate.setMotherName(params.getMotherName());
            studentToUpdate.setActive(params.isActive());
            Student updatedStudent = studentRepository.save(studentToUpdate);
            StudentResponseDto responseDto = studentMapper.toResponseDto(updatedStudent);
            return ApiResponse.<StudentResponseDto>builder()
                    .statusCode(200)
                    .message("Student updated successfully")
                    .data(responseDto)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<StudentResponseDto>builder()
                    .statusCode(500)
                    .message("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    public ApiResponse<StudentResponseDto> deleteStudent(UUID id) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isEmpty()) {
                return ApiResponse.<StudentResponseDto>builder()
                        .statusCode(404)
                        .message("Student not found")
                        .build();
            }
            Student student = studentOptional.get();
            if (student.getIsDeleted() == 1) {
                return ApiResponse.<StudentResponseDto>builder()
                        .statusCode(409)
                        .message("Student is already deleted")
                        .build();
            }
            student.setIsDeleted(1);
            student.setActive(false);
            Student deletedStudent = studentRepository.save(student);
            StudentResponseDto responseDto = studentMapper.toResponseDto(deletedStudent);
            return ApiResponse.<StudentResponseDto>builder()
                    .statusCode(200)
                    .message("Student marked as deleted")
                    .data(responseDto)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<StudentResponseDto>builder()
                    .statusCode(500)
                    .message("Internal server error: " + e.getMessage())
                    .build();
        }
    }
}