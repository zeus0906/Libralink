package com.api.gesboo.service;

import com.api.gesboo.entite.Role;
import com.api.gesboo.entite.Utilisateur;
import com.api.gesboo.entite.Validation;
import com.api.gesboo.enums.TypeDeRole;
import com.api.gesboo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurService {

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



}
