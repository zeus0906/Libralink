package com.api.gesboo.entite.User;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name= "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idRole;

    @Enumerated(EnumType.STRING)
    private TypeRoles libelle;
}
