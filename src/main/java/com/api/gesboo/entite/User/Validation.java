package com.api.gesboo.entite.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Validation")
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValidation;
    private Instant creation;
    private Instant activation;
    private Instant expiration;
    private String code;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private Utilisateur utilisateur;
}
