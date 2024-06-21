package com.api.gesboo.repository;


import com.api.gesboo.entite.Validation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ValidationRepository extends CrudRepository<Validation, Integer> {

    Optional<Validation> findByCode(String code);
    void deleteAllByExpirationBefore(Instant now);
}
