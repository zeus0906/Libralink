package com.api.gesboo.repository;


import com.api.gesboo.entite.Categorie;
import com.api.gesboo.enums.CategorieType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Categorie, Integer> {
    Optional<Categorie> findByType(CategorieType type);
}
