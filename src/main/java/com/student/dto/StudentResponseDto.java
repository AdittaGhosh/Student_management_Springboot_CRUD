package com.student.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StudentResponseDto {
    private UUID id;
    private String name;
    private String dob;
    private String fatherName;
    private String motherName;
    private boolean active;
    private int isDeleted;
}