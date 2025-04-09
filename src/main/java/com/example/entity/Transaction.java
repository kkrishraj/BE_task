package com.example.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
			name = "transactions"
)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccountDetails accountDetails;

    private String type;
    private Double amount;
    private Timestamp createdAt;

    public Transaction(UserAccountDetails accountDetails, String type, Double amount) {
        this.accountDetails = accountDetails;
        this.type = type;
        this.amount = amount;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

   
}