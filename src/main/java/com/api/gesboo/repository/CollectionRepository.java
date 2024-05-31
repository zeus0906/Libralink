package com.api.gesboo.repository;

import com.api.gesboo.entite.Book.Collection;
import com.api.gesboo.entite.Book.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    Optional<Collection> findByType(CollectionType type);
}
