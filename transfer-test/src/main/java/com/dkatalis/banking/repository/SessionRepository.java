package com.dkatalis.banking.repository;

import java.util.Optional;

import com.dkatalis.banking.model.Session;

public interface SessionRepository extends CountableRepository, CrudRepository<Session, String> {

    Optional<Session> getActiveSession(String username);
    
}
