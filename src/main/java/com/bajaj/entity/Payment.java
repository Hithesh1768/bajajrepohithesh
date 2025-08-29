package com.bajaj.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENTS")
@Data
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Integer paymentId;
    
    @Column(name = "EMP_ID")
    private Integer empId;
    
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    
    @Column(name = "PAYMENT_TIME")
    private LocalDateTime paymentTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_ID", insertable = false, updatable = false)
    private Employee employee;
}
