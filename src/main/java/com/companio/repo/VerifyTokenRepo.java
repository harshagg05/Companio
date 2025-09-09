package com.companio.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.companio.model.VerifyToken;

@Repository
public interface VerifyTokenRepo extends JpaRepository<VerifyToken,Integer>{
    Optional<VerifyToken> findByToken(String token);
}
