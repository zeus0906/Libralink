package com.api.gesboo.controller;


import com.api.gesboo.Dto.AuthentificationDto;
import com.api.gesboo.configuration.JwtService;
import com.api.gesboo.entite.User.Utilisateur;
import com.api.gesboo.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @CrossOrigin
    @PostMapping(path = "inscription")
    public void inscription(@RequestBody Utilisateur utilisateur){
        log.info("Inscription");
        this.utilisateurService.inscription(utilisateur);
    }

    @CrossOrigin
    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation){
        this.utilisateurService.activation(activation);
    }

    @CrossOrigin
    @PostMapping(path = "modifier-mot-de-passe")
    public void modifierMotDePasse(@RequestBody Map<String, String> email){
        this.utilisateurService.modifierMotDePasse(email);
    }

    @CrossOrigin
    @PostMapping(path = "nouveau-mot-de-passe")
    public void nouveauMotDePasse(@RequestBody Map<String, String> activation){
        this.utilisateurService.nouveauMotDePasse(activation);
    }

    @CrossOrigin
    @PostMapping(path = "refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return this.jwtService.refreshToken(refreshTokenRequest);
    }

    @CrossOrigin
    @PostMapping(path = "deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
    }

    @PostMapping(path = "connexion")
    @CrossOrigin
    public Map<String, String> connexion(@RequestBody AuthentificationDto authentificationDto){
        final Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDto.username(), authentificationDto.password()
                )
        );

        if (authentication.isAuthenticated()){
            return this.jwtService.generate(authentificationDto.username());
        }
        return null;
    }

    @CrossOrigin
    @GetMapping(path = "lecture")
    public void lectureTest(){
         System.out.println("Test ok");
    }
}
