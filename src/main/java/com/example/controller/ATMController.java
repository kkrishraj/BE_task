package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Request;
import com.example.model.Response;
import com.example.service.ATMService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/atm")
@Slf4j
public class ATMController {

    @Autowired
    private ATMService atmService;

    @PostMapping("/deposit")
    public ResponseEntity<Response> deposit(@RequestBody Request request) {
    	log.info("deposit method BEGIN");
    	log.info("accountID:---->"+request.getAccountId());
    	Response res =atmService.deposit(request.getAccountId(), request.getAmount());
    	log.info("deposit method END");
        return ResponseEntity.ok(res);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Response> withdraw(@RequestBody Request request) {
    	log.info("withdraw method BEGIN");
    	log.info("accountID:---->"+request.getAccountId());
    	Response res = atmService.withdraw(request.getAccountId(), request.getAmount());
    	log.info("withdraw method END");
        return ResponseEntity.ok(res);
    }
}