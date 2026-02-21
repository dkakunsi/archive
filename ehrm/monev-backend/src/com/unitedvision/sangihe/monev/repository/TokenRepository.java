package com.unitedvision.sangihe.monev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.monev.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Token findByToken(String token);

}
