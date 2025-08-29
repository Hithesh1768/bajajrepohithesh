package com.bajaj.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "DEPARTMENT")
@Data
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;
    
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
}
