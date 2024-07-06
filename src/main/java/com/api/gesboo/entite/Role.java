package com.api.gesboo.entite;

import com.api.gesboo.enums.TypeDeRole;
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
    private TypeDeRole libelle;
}
