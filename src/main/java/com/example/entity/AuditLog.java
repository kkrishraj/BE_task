package com.example.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table(
			name = "auditLog"
)
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;
    
    @Column(name = "received_at", nullable = false)
    private Timestamp receivedAt;

    public AuditLog() {
        this.receivedAt = new Timestamp(System.currentTimeMillis());
    }

    public AuditLog(String message) {
        this.message = message;
        this.receivedAt = new Timestamp(System.currentTimeMillis());
    }

}