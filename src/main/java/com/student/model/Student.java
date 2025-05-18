package com.student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Student {
    @Id
    private int id;
    private String name;

    @JsonProperty("DOB")
    private String DOB;

    private String fatherName;
    private String motherName;

    public Student() {
        super();
    }

    public Student(int id, String name, String DOB, String fatherName, String motherName) {
        super();
        this.id = id;
        this.name = name;
        this.DOB = DOB;
        this.fatherName = fatherName;
        this.motherName = motherName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", DOB='" + DOB + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                '}';
    }
}