package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.entity.AuditLog;
import com.example.repo.AuditLogRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @KafkaListener(topics = "transaction-logs", groupId = "audit-log")
    public void consume(String message) {
    	log.info("Kafka Called");
        AuditLog log = new AuditLog(message);
        auditLogRepository.save(log);
    }
}