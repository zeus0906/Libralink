package com.api.gesboo.repository;

import com.api.gesboo.entite.Collection;
import com.api.gesboo.entite.CollectionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    Object findByType(CollectionType collectionType);
}
