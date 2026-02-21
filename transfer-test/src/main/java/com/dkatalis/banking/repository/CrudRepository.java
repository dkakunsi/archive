package com.dkatalis.banking.repository;

import java.util.Optional;

public interface CrudRepository<T, ID> {
        
    default T save(T entity) {
        throw new UnsupportedOperationException();
    }

    default Optional<T> findOne(ID id) {
        throw new UnsupportedOperationException();
    }
}
