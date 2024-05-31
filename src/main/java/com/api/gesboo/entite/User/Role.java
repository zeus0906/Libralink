package com.api.gesboo.entite.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idRole;

    @Enumerated(EnumType.STRING)
    private TypeRoles libelle;

}
