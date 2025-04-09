package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.entity.AuditLog;
import com.example.entity.Transaction;
import com.example.entity.UserAccountDetails;
import com.example.model.Response;
import com.example.repo.AuditLogRepository;
import com.example.repo.TransactionRepository;
import com.example.repo.UserAccountRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ATMService {

    @Autowired
    private UserAccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String topic = "transaction-logs";

    @Transactional
    public Response deposit(Integer accountId, Double amount) {
    	
    	log.info("deposit method BEGIN");
    	Optional<UserAccountDetails> account = accountRepository.findById(accountId);
    	if(account.isEmpty()) {
    		 auditLogRepository.save(new AuditLog(accountId+"- DEPOSIT FAILED - INVALID ACCOUNTID- "+amount));
    		return processResponse(false,"invalid accountId");
    	} else {
    	
        account.get().setBalance(account.get().getBalance()+amount);
        accountRepository.save(account.get());

        Transaction txn = new Transaction(account.get(), "DEPOSIT", amount);
        transactionRepository.save(txn);
        
        
        auditLogRepository.save(new AuditLog(accountId+"- DEPOSIT - "+amount));
        
        //kafkaTemplate.send(topic, "Account " + accountId + " DEPOSIT " + amount);
        log.info("deposit method END");
        return processResponse(true,"Deposit successful");
    	} 
    }

    @Transactional
    public Response withdraw(Integer accountId, Double amount) {
    	log.info("withdraw method BEGIN");
    	Optional<UserAccountDetails> account = accountRepository.findById(accountId);
    	if(account.isEmpty()) {
	   		 auditLogRepository.save(new AuditLog(accountId+"- WITHDRAWAL FAILED - INVALID ACCOUNTID- "+amount));
	   		 return processResponse(false,"invalid accountId");
	   	} else {
	        if (account.get().getBalance().compareTo(amount) < 0) {
	        	auditLogRepository.save(new AuditLog(accountId+"- WITHDRAWAL FAILURE - "+amount));
	            return processResponse(false,"Insufficient funds");
	        }
	        account.get().setBalance(account.get().getBalance() - amount);
	        accountRepository.save(account.get());
	
	        Transaction txn = new Transaction(account.get(), "WITHDRAWAL", amount);
	        transactionRepository.save(txn);
	        
	        auditLogRepository.save(new AuditLog(accountId+"- WITHDRAWAL SUCCESS - "+amount));
	        //kafkaTemplate.send(topic, "Account " + accountId + " WITHDRAWAL " + amount);
	        log.info("withdraw method END");
	        return processResponse(true,"Withdrawal successful");
	   	}
    }
    
    private Response processResponse(boolean status,String message) {
    	return new Response(status,message);
    }
}