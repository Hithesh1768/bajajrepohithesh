package com.bajaj.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "EMPLOYEE")
@Data
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_ID")
    private Integer empId;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Column(name = "DOB")
    private LocalDate dob;
    
    @Column(name = "GENDER")
    private String gender;
    
    @Column(name = "DEPARTMENT")
    private Integer department;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT", insertable = false, updatable = false)
    private Department departmentInfo;
}
