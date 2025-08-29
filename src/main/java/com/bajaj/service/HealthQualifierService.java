package com.bajaj.service;

import com.bajaj.dto.SolutionRequest;
import com.bajaj.dto.WebhookRequest;
import com.bajaj.dto.WebhookResponse;
import com.bajaj.entity.Department;
import com.bajaj.entity.Employee;
import com.bajaj.entity.Payment;
import com.bajaj.repository.DepartmentRepository;
import com.bajaj.repository.EmployeeRepository;
import com.bajaj.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@Slf4j
public class HealthQualifierService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private static final String WEBHOOK_GENERATION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String WEBHOOK_SUBMISSION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

    @PostConstruct
    public void runQualifier() {
        try {
            log.info("Starting Bajaj Health Qualifier process...");

            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            if (webhookResponse == null) {
                log.error("Failed to generate webhook. Exiting.");
                return;
            }

            log.info("Webhook generated successfully: {}", webhookResponse.getWebhook());

            // Step 2: Initialize database with sample data
            initializeDatabase();

            // Step 3: Solve the SQL problem
            String sqlQuery = solveSQLProblem();
            log.info("SQL Query generated: {}", sqlQuery);

            // Step 4: Submit solution
            boolean submissionSuccess = submitSolution(webhookResponse.getAccessToken(), sqlQuery);

            if (submissionSuccess) {
                log.info("Qualifier completed successfully!");
            } else {
                log.error("Failed to submit solution.");
            }

        } catch (Exception e) {
            log.error("Error during qualifier process", e);
        }
    }

    private WebhookResponse generateWebhook() {
        try {
            WebhookRequest request = new WebhookRequest();
            request.setName("John Doe");
            request.setRegNo("REG12347");
            request.setEmail("john@example.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                    WEBHOOK_GENERATION_URL, entity, WebhookResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }

        } catch (Exception e) {
            log.error("Error generating webhook", e);
        }
        return null;
    }

    private void initializeDatabase() {
        log.info("Initializing database with sample data...");

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

        Department sales = new Department();
        sales.setDepartmentId(4);
        sales.setDepartmentName("Sales");
        departmentRepository.save(sales);

        Department marketing = new Department();
        marketing.setDepartmentId(5);
        marketing.setDepartmentName("Marketing");
        departmentRepository.save(marketing);

        Department it = new Department();
        it.setDepartmentId(6);
        it.setDepartmentName("IT");
        departmentRepository.save(it);

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

        Employee emp3 = new Employee();
        emp3.setEmpId(3);
        emp3.setFirstName("Michael");
        emp3.setLastName("Smith");
        emp3.setDob(LocalDate.of(1985, 2, 10));
        emp3.setGender("Male");
        emp3.setDepartment(3);
        employeeRepository.save(emp3);

        Employee emp4 = new Employee();
        emp4.setEmpId(4);
        emp4.setFirstName("Emily");
        emp4.setLastName("Brown");
        emp4.setDob(LocalDate.of(1992, 11, 30));
        emp4.setGender("Female");
        emp4.setDepartment(4);
        employeeRepository.save(emp4);

        Employee emp5 = new Employee();
        emp5.setEmpId(5);
        emp5.setFirstName("David");
        emp5.setLastName("Jones");
        emp5.setDob(LocalDate.of(1988, 9, 5));
        emp5.setGender("Male");
        emp5.setDepartment(5);
        employeeRepository.save(emp5);

        Employee emp6 = new Employee();
        emp6.setEmpId(6);
        emp6.setFirstName("Olivia");
        emp6.setLastName("Davis");
        emp6.setDob(LocalDate.of(1995, 4, 12));
        emp6.setGender("Female");
        emp6.setDepartment(1);
        employeeRepository.save(emp6);

        Employee emp7 = new Employee();
        emp7.setEmpId(7);
        emp7.setFirstName("James");
        emp7.setLastName("Wilson");
        emp7.setDob(LocalDate.of(1983, 3, 25));
        emp7.setGender("Male");
        emp7.setDepartment(6);
        employeeRepository.save(emp7);

        Employee emp8 = new Employee();
        emp8.setEmpId(8);
        emp8.setFirstName("Sophia");
        emp8.setLastName("Anderson");
        emp8.setDob(LocalDate.of(1991, 8, 17));
        emp8.setGender("Female");
        emp8.setDepartment(4);
        employeeRepository.save(emp8);

        Employee emp9 = new Employee();
        emp9.setEmpId(9);
        emp9.setFirstName("Liam");
        emp9.setLastName("Miller");
        emp9.setDob(LocalDate.of(1979, 12, 1));
        emp9.setGender("Male");
        emp9.setDepartment(1);
        employeeRepository.save(emp9);

        Employee emp10 = new Employee();
        emp10.setEmpId(10);
        emp10.setFirstName("Emma");
        emp10.setLastName("Taylor");
        emp10.setDob(LocalDate.of(1993, 6, 28));
        emp10.setGender("Female");
        emp10.setDepartment(5);
        employeeRepository.save(emp10);

        // Create payments (excluding 1st day of month)
        createPayment(2, new BigDecimal("65784.00"), LocalDateTime.of(2025, 1, 6, 18, 36, 37, 892000000));
        createPayment(4, new BigDecimal("62736.00"), LocalDateTime.of(2025, 1, 6, 18, 36, 37, 892000000));
        createPayment(1, new BigDecimal("69437.00"), LocalDateTime.of(2025, 1, 2, 17, 21, 57, 341000000));
        createPayment(3, new BigDecimal("67183.00"), LocalDateTime.of(2025, 1, 2, 17, 21, 57, 341000000));
        createPayment(2, new BigDecimal("66273.00"), LocalDateTime.of(2025, 2, 1, 11, 49, 15, 764000000));
        createPayment(5, new BigDecimal("71475.00"), LocalDateTime.of(2025, 1, 2, 7, 24, 14, 453000000));
        createPayment(1, new BigDecimal("70837.00"), LocalDateTime.of(2025, 2, 3, 19, 11, 31, 553000000));
        createPayment(6, new BigDecimal("69628.00"), LocalDateTime.of(2025, 1, 2, 10, 41, 15, 113000000));
        createPayment(4, new BigDecimal("71876.00"), LocalDateTime.of(2025, 2, 1, 12, 16, 47, 807000000));
        createPayment(3, new BigDecimal("70098.00"), LocalDateTime.of(2025, 2, 3, 10, 11, 17, 341000000));
        createPayment(6, new BigDecimal("67827.00"), LocalDateTime.of(2025, 2, 2, 19, 21, 27, 753000000));
        createPayment(5, new BigDecimal("69871.00"), LocalDateTime.of(2025, 2, 5, 17, 54, 17, 453000000));
        createPayment(2, new BigDecimal("72984.00"), LocalDateTime.of(2025, 3, 5, 9, 37, 35, 974000000));
        createPayment(1, new BigDecimal("67982.00"), LocalDateTime.of(2025, 3, 1, 6, 9, 51, 983000000));
        createPayment(6, new BigDecimal("70198.00"), LocalDateTime.of(2025, 3, 2, 10, 34, 35, 753000000));
        createPayment(4, new BigDecimal("74998.00"), LocalDateTime.of(2025, 3, 2, 9, 27, 26, 162000000));

        log.info("Database initialized successfully");
    }

    private void createPayment(Integer empId, BigDecimal amount, LocalDateTime paymentTime) {
        Payment payment = new Payment();
        payment.setEmpId(empId);
        payment.setAmount(amount);
        payment.setPaymentTime(paymentTime);
        paymentRepository.save(payment);
    }

    private String solveSQLProblem() {
        log.info("Solving SQL problem...");

        // Based on the problem description, we need to find:
        // 1. Highest salary not credited on 1st day of month
        // 2. Employee name, age, and department

        // First, find the highest payment amount (excluding 1st day)
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

        if (maxPayment == null) {
            log.error("No valid payment found");
            return "";
        }

        // Get employee details
        Employee employee = employeeRepository.findById(maxPayment.getEmpId()).orElse(null);
        if (employee == null) {
            log.error("Employee not found");
            return "";
        }

        // Get department details
        Department department = departmentRepository.findById(employee.getDepartment()).orElse(null);
        if (department == null) {
            log.error("Department not found");
            return "";
        }

        // Calculate age
        int age = Period.between(employee.getDob(), LocalDate.now()).getYears();

        log.info("Solution found - Max Amount: {}, Employee: {} {}, Age: {}, Department: {}",
                maxAmount, employee.getFirstName(), employee.getLastName(), age, department.getDepartmentName());

        // Generate the SQL query
        String sqlQuery =
                "SELECT p.AMOUNT as SALARY, " +
                        "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME, " +
                        "YEAR(CURDATE()) - YEAR(e.DOB) - (DATE_FORMAT(CURDATE(), '%m%d') < DATE_FORMAT(e.DOB, '%m%d')) as AGE, " +
                        "d.DEPARTMENT_NAME " +
                        "FROM PAYMENTS p " +
                        "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                        "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                        "WHERE DAY(p.PAYMENT_TIME) != 1 " +
                        "ORDER BY p.AMOUNT DESC " +
                        "LIMIT 1";

        return sqlQuery;
    }

    private boolean submitSolution(String accessToken, String sqlQuery) {
        try {
            SolutionRequest request = new SolutionRequest();
            request.setFinalQuery(sqlQuery);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    WEBHOOK_SUBMISSION_URL, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Solution submitted successfully. Response: {}", response.getBody());
                return true;
            } else {
                log.error("Failed to submit solution. Status: {}", response.getStatusCode());
                return false;
            }

        } catch (Exception e) {
            log.error("Error submitting solution", e);
            return false;
        }
    }
}
