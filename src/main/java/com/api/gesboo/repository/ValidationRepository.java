package com.api.gesboo.repository;

import com.api.gesboo.entite.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {

    Optional<Validation> findByCode(String code);
}
