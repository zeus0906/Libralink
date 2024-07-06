package com.api.gesboo.entite;

import com.api.gesboo.enums.TypeDeRole;
import com.api.gesboo.repository.UtilisateurRepository;
import com.api.gesboo.service.ValidationService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean desactive;
    private boolean expire;
    private String valeur;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private RefreshToken refreshToken;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name="utlisateur_id")
    private Utilisateur utilisateur;

    @AllArgsConstructor
    @Service
    public static class UtilisateurService {

        private UtilisateurRepository utilisateurRepository;

        private BCryptPasswordEncoder passwordEncoder;

        private ValidationService validationService;

        // Inscription
        public void inscrire(Utilisateur utilisateur) {

            if(!utilisateur.getEmail().contains("@")) {
                throw new RuntimeException("Email invalid");
            }
            if(!utilisateur.getEmail().contains(".")) {
                throw new RuntimeException("Email invalid");
            }

            Optional<Utilisateur> utilisateurOptional =  this.utilisateurRepository.findByEmail(utilisateur.getEmail());
            if(utilisateurOptional.isPresent()) {
                throw new RuntimeException("votre mail est déjà utilisé");
            }

            String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
            utilisateur.setMdp(mdpCrypte);

            Role roleUtilisateur = new Role();
            roleUtilisateur.setLibelle(TypeDeRole.UTILISATEUR);
            utilisateur.setRole(roleUtilisateur);

            utilisateur = this.utilisateurRepository.save(utilisateur);
            this.validationService.enregistrer(utilisateur);
        }

        //Validation du compte
        public void activation(Map<String, String> activation) {
            Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
            if(Instant.now().isAfter(validation.getExpiration())){
                throw new RuntimeException("votre code à expire");
            }
            Utilisateur utilisateurActiver =  this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("L'utilisateur est inconnu"));
            utilisateurActiver.setActif(true);
            this.utilisateurRepository.save(utilisateurActiver);
        }


        public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
            return this.utilisateurRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
        }

        // Cette méthode est en pause pour des modifications car nous rencontrons un  petit probème où quand nous voulons modifier et
        // ...que nous envoyons notre mail on a une duplication au lieu de l'envoie du code par mail
        public void modifierMotDePasse(Map<String, String> parametres) {
            Utilisateur utilisateur = this.loadUserByUsername(parametres.get("email"));
            this.validationService.enregistrer(utilisateur);
        }

        // Cette méthode doit passer au test car il dépend de la méthode "modificationMotDePasse
        public void nouveauMotDePasse(Map<String, String> parametres) {
            Utilisateur utilisateur = this.loadUserByUsername(parametres.get("email"));
            final Validation validation = validationService.lireEnFonctionDuCode(parametres.get("code"));
            if(validation.getUtilisateur().getEmail().equals(utilisateur.getEmail())) {
                String passwordCrypte = this.passwordEncoder.encode(parametres.get("password"));
                utilisateur.setMdp(passwordCrypte);
                this.utilisateurRepository.save(utilisateur);
            }
        }
    }
}
