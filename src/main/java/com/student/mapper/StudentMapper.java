package com.student.mapper;

import com.student.dto.StudentResponseDto;
import com.student.entity.Student;
import com.student.params.StudentCreateRequestParams;
import com.student.params.StudentUpdateRequestParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "dob", source = "dob", qualifiedByName = "formatDate")
    StudentResponseDto toResponseDto(Student student);

    List<StudentResponseDto> toResponseListDto(List<Student> students);

    @Mapping(target = "dob", source = "dob", qualifiedByName = "formatDate")
    Student toEntity(StudentCreateRequestParams params);

    @Mapping(target = "dob", source = "dob", qualifiedByName = "formatDate")
    Student toEntity(StudentUpdateRequestParams params);

    @Named("formatDate")
    default String formatDate(String dob) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-mm-yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
            Date date = inputFormat.parse(dob);
            return outputFormat.format(date);
        } catch (Exception e) {
            return dob; // Default to original if parsing fails
        }
    }
}