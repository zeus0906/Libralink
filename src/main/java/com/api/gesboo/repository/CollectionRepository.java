package com.api.gesboo.repository;

import com.api.gesboo.entite.BookCollection;
import com.api.gesboo.entite.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<BookCollection, Long> {
    Optional<BookCollection> findByType(CollectionType type);
}