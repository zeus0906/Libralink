package com.api.gesboo.controller;

import com.api.gesboo.entite.Avis;
import com.api.gesboo.entite.AvisDto;
import com.api.gesboo.entite.Reply;
import com.api.gesboo.service.AvisService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AvisController {

    private AvisService avisService;

    @PostMapping("/ajouterAvis")
    public ResponseEntity<Avis> ajouterAvis(@RequestParam long utilisateurId,
                                            @RequestParam Long bookId,
                                            @RequestBody AvisDto avisDto){
        Avis avis = avisService.addAvis(utilisateurId, bookId, avisDto);
        return avis != null ? ResponseEntity.ok(avis) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Avis> updateAvis(@PathVariable int id, @RequestBody AvisDto avisDto) {
        Avis avis = avisService.updateAvis(id, avisDto);
        return avis != null ? ResponseEntity.ok(avis) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAvis(@PathVariable int id) {
        avisService.deleteAvis(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Avis>> getAvisByBook(@PathVariable int bookId) {
        List<Avis> avis = avisService.getAvisByBook(bookId);
        return ResponseEntity.ok(avis);
    }

    @PostMapping("/reply/add")
    public ResponseEntity<Reply> addReply(@RequestParam int userId,
                                          @RequestParam int reviewId,
                                          @RequestBody String content) {
        Reply reply = avisService.ajouteReply(userId, reviewId, content);
        return reply != null ? ResponseEntity.ok(reply) : ResponseEntity.badRequest().build();
    }
}
