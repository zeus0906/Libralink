package com.api.gesboo.service;

import com.api.gesboo.entite.Avis;
import com.api.gesboo.entite.Book;
import com.api.gesboo.entite.Reply;
import com.api.gesboo.entite.Utilisateur;
import com.api.gesboo.repository.AvisRepository;
import com.api.gesboo.repository.BookRepository;
import com.api.gesboo.repository.ReplyRepository;
import com.api.gesboo.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AvisService {

    private ReplyRepository replyRepository;

    private AvisRepository avisRepository;

    private BookRepository bookRepository;

    private UtilisateurRepository utilisateurRepository;

    // Permet de mettre un avis sur livre
    public Avis addAvis(int utilisateurId, Long bookId, int note, String contenu) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(utilisateurId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (utilisateurOptional.isPresent() && bookOptional.isPresent()) {
            Avis avis = new Avis();
            avis.setUtilisateur(utilisateurOptional.get());
            avis.setBook(bookOptional.get());
            avis.setNote(note);
            avis.setContenu(contenu);
            return avisRepository.save(avis);
        }
        return null;
    }

    // Permet de faire la mise à jour d'un Avis
    public Avis updateAvis(int avisId, int note, String contenu) {
        Optional<Avis> avisOptional = avisRepository.findById(avisId);
        if (avisOptional.isPresent()) {
            Avis avis = avisOptional.get();
            avis.setNote(note);
            avis.setContenu(contenu);
            return avisRepository.save(avis);
        }
        return null;
    }

    //Supprimer un avis
    public void deleteAvis(int avisId) {
        avisRepository.deleteById(avisId);
    }

    //Liste des avis d'un livre
    public List<Avis> getAvisByBook(int bookId){
        return avisRepository.findByBookId(bookId);
    }

    public Reply ajouteReply(int utilisateurId, int avisId, String contenu){
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(utilisateurId);
        Optional<Avis> avisOptional = avisRepository.findById(avisId);

        if (utilisateurOptional.isPresent() && avisOptional.isPresent()){
            Reply reply = new Reply();
            reply.setUtilisateur(utilisateurOptional.get());
            reply.setAvis(avisOptional.get());
            reply.setContenu(contenu);
            return replyRepository.save(reply);
        }

        return null;
    }
}
