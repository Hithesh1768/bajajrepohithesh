package com.bajaj;

import com.bajaj.entity.Department;
import com.bajaj.entity.Employee;
import com.bajaj.entity.Payment;
import com.bajaj.repository.DepartmentRepository;
import com.bajaj.repository.EmployeeRepository;
import com.bajaj.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class HealthQualifierServiceTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        // Clear existing data
        paymentRepository.deleteAll();
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();

        // Create test data
        createTestData();
    }

    @Test
    void testSQLProblemSolution() {
        // Find the highest payment amount (excluding 1st day)
        List<Payment> payments = paymentRepository.findAll();
        
        BigDecimal maxAmount = BigDecimal.ZERO;
        Payment maxPayment = null;
        
        for (Payment payment : payments) {
            if (payment.getPaymentTime().getDayOfMonth() != 1 && 
                payment.getAmount().compareTo(maxAmount) > 0) {
                maxAmount = payment.getAmount();
                maxPayment = payment;
            }
        }
        
        assertNotNull(maxPayment, "Should find a valid payment");
        assertNotEquals(BigDecimal.ZERO, maxAmount, "Max amount should not be zero");
        
        // Get employee details
        Employee employee = employeeRepository.findById(maxPayment.getEmpId()).orElse(null);
        assertNotNull(employee, "Employee should exist");
        
        // Get department details
        Department department = departmentRepository.findById(employee.getDepartment()).orElse(null);
        assertNotNull(department, "Department should exist");
        
        // Calculate age
        int age = java.time.Period.between(employee.getDob(), LocalDate.now()).getYears();
        assertTrue(age > 0, "Age should be positive");
        
        System.out.println("Test Results:");
        System.out.println("Max Amount: " + maxAmount);
        System.out.println("Employee: " + employee.getFirstName() + " " + employee.getLastName());
        System.out.println("Age: " + age);
        System.out.println("Department: " + department.getDepartmentName());
    }

    private void createTestData() {
        // Create departments
        Department hr = new Department();
        hr.setDepartmentId(1);
        hr.setDepartmentName("HR");
        departmentRepository.save(hr);
        
        Department finance = new Department();
        finance.setDepartmentId(2);
        finance.setDepartmentName("Finance");
        departmentRepository.save(finance);
        
        Department engineering = new Department();
        engineering.setDepartmentId(3);
        engineering.setDepartmentName("Engineering");
        departmentRepository.save(engineering);
        
        // Create employees
        Employee emp1 = new Employee();
        emp1.setEmpId(1);
        emp1.setFirstName("John");
        emp1.setLastName("Williams");
        emp1.setDob(LocalDate.of(1980, 5, 15));
        emp1.setGender("Male");
        emp1.setDepartment(3);
        employeeRepository.save(emp1);
        
        Employee emp2 = new Employee();
        emp2.setEmpId(2);
        emp2.setFirstName("Sarah");
        emp2.setLastName("Johnson");
        emp2.setDob(LocalDate.of(1990, 7, 20));
        emp2.setGender("Female");
        emp2.setDepartment(2);
        employeeRepository.save(emp2);
        
        // Create payments (excluding 1st day of month)
        createPayment(1, new BigDecimal("69437.00"), LocalDateTime.of(2025, 1, 2, 17, 21, 57));
        createPayment(2, new BigDecimal("72984.00"), LocalDateTime.of(2025, 3, 5, 9, 37, 35));
        createPayment(1, new BigDecimal("70837.00"), LocalDateTime.of(2025, 2, 3, 19, 11, 31));
    }

    private void createPayment(Integer empId, BigDecimal amount, LocalDateTime paymentTime) {
        Payment payment = new Payment();
        payment.setEmpId(empId);
        payment.setAmount(amount);
        payment.setPaymentTime(paymentTime);
        paymentRepository.save(payment);
    }
}
