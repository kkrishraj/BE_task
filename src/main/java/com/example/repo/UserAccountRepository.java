package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.UserAccountDetails;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountDetails, Integer> {
}