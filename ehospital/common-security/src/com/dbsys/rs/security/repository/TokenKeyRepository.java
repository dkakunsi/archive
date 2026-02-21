package com.dbsys.rs.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsys.rs.lib.entity.Token;

public interface TokenKeyRepository extends JpaRepository<Token, String> { }
