package com.api.gesboo.service;

import com.api.gesboo.entite.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {


    JavaMailSender javaMailSender;

    public void envoyerNotification(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@gmail.com");
        message.setTo(validation.getUtilisateur().getEmail());
        message.setSubject("Votre code d'activation de votre compte");

      String texte = String.format(
                "Bonjour %s, <br /> Votre code d'activation  est %s; A bientot ",
                validation.getUtilisateur().getUsername(),
                validation.getCode()
                );
        message.setText(texte);

        javaMailSender.send(message);

    }
}
