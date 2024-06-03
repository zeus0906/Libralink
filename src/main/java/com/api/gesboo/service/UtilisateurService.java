package com.api.gesboo.service;


import com.api.gesboo.entite.User.Role;
import com.api.gesboo.entite.User.TypeRoles;
import com.api.gesboo.entite.User.Utilisateur;
import com.api.gesboo.entite.User.Validation;
import com.api.gesboo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ValidationService validationService;

    public void inscription(Utilisateur utilisateur){

        if(!utilisateur.getEmail().contains("@")){
            throw new RuntimeException("Votre mail est invalide");
        }
        if(!utilisateur.getEmail().contains(".")){
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if(utilisateurOptional.isPresent()){
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

        String passwordCrypte = this.passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(passwordCrypte);

        Role roleUtilisateur = new Role();
        roleUtilisateur.setLibelle(TypeRoles.LECTEUR);
        utilisateur.setRole(roleUtilisateur);

        utilisateur = this.utilisateurRepository.save(utilisateur);
        this.validationService.enregistrer(utilisateur);
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));

        if(Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Votre code a expiré");
        }

       Utilisateur utilisateurActiver =  this.utilisateurRepository.findById(Math.toIntExact(validation.getUtilisateur().getIdUtilisateur())).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        this.utilisateurRepository.save(utilisateurActiver);
    }

    @Override
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
            utilisateur.setPassword(passwordCrypte);
            this.utilisateurRepository.save(utilisateur);
        }
    }
}
