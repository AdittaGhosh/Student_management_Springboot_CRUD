package com.student.utils.url;

public interface UrlStudentPaths extends UrlBaseSupplier {
    String BASE = API_V1 + "/students"; // Updated to align with API_V1
    String STUDENT_LIST = "/list";
    String BY_ID = "/{id}";
    String UPDATE = "/{id}";
    String DELETE = "/{id}";
}