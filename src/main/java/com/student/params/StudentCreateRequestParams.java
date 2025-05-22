package com.student.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentCreateRequestParams {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "DOB is required")
    private String dob;

    @NotBlank(message = "Father's name is required")
    private String fatherName;

    @NotBlank(message = "Mother's name is required")
    private String motherName;
}