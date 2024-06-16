package com.api.gesboo.controller;

import com.api.gesboo.entite.Utilisateur;
import com.api.gesboo.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
public class UtilisateurController {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);

    private UtilisateurService utilisateurService;

    @PostMapping(path = "/inscription")
    public void inscription(@RequestBody Utilisateur utilisateur){
        this.utilisateurService.inscrire(utilisateur);
        log.info("Inscription");
    }

    @PostMapping(path = "/activation")
    public void activation(@RequestBody Map<String, String> activation){
        this.utilisateurService.activation(activation);
        log.info("Inscription");
    }
}
