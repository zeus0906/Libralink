package com.api.gesboo.controller;

import com.api.gesboo.Dto.AuthentificationDto;
import com.api.gesboo.entite.Jwt;
import com.api.gesboo.entite.Utilisateur;
import com.api.gesboo.securite.JwtService;
import com.api.gesboo.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
public class UtilisateurController {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/inscription")
    public void inscription(@RequestBody Utilisateur utilisateur){
        this.utilisateurService.inscription(utilisateur);
        log.info("Inscription");
    }

    @PostMapping(path = "/activation")
    public void activation(@RequestBody Map<String, String> activation){
        this.utilisateurService.activation(activation);
        log.info("Inscription");
    }

    @CrossOrigin
    @PostMapping(path = "/modifier-mot-de-passe")
    public void modifierMotDePasse(@RequestBody Map<String, String> email){
        this.utilisateurService.modifierMotDePasse(email);
    }

    @CrossOrigin
    @PostMapping(path = "/nouveau-mot-de-passe")
    public void nouveauMotDePasse(@RequestBody Map<String, String> activation){
        this.utilisateurService.nouveauMotDePasse(activation);
    }

    @CrossOrigin
    @PostMapping(path = "/refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return this.jwtService.refreshToken(refreshTokenRequest);
    }

    @CrossOrigin
    @PostMapping(path = "/deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
    }

    @PostMapping(path = "/connexion")
    @CrossOrigin
    public Map<String, String> connexion(@RequestBody AuthentificationDto authentificationDto){
        final Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDto.username(), authentificationDto.mdp()
                )
        );

        if (authentication.isAuthenticated()){
            return this.jwtService.generate(authentificationDto.username());
        }
        return null;
    }
}
