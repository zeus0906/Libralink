package com.api.gesboo.repository;

import com.api.gesboo.entite.BookCollection;
import com.api.gesboo.entite.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<BookCollection,Long> {
    Optional<BookCollection> findByType(CollectionType type);
}
