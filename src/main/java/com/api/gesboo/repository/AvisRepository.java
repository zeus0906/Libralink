package com.api.gesboo.repository;

import com.api.gesboo.entite.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Integer> {
    List<Avis> findByBookId(int bookId);
}
