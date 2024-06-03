package com.api.gesboo.service;


import com.api.gesboo.entite.User.Validation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void envoyer(Validation validation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("frankynyambi20@gmail.com");
        message.setTo(validation.getUtilisateur().getEmail());
        message.setSubject("Votre code d'activation");

        String texte = String.format("Bonjour %s, <br /> Votre code d'activation est %s; A bientot",
                validation.getUtilisateur().getNom(),
                validation.getCode());
        message.setText(texte);

        javaMailSender.send(message);
    }
}
