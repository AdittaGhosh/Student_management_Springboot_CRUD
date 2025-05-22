package com.student.utils.url;

public interface UrlStudentPaths extends UrlBaseSupplier {
    String BASE = "/student" + "/v1";
    String STUDENT_LIST = "/students";
    String BY_ID = "/students/{id}";
    String UPDATE = "/students/{id}";
    String DELETE = "/students/{id}";
}