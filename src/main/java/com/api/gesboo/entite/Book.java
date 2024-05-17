package com.api.gesboo.entite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "Book")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "Titre")
    private String title;

    @Column(name = "Sous-Titre")
    private String subtitle;

    @Column(name = "parDéclaration")
    private String byStatement;

    @Column(name = "Date_Publication")
    private String publishDate;

    @Column(name = "Langue")
    private String language;

    @Column(name = "Editeurs")
    private String publishers;

    @Column(name = "NombreDePages")
    private int numberOfPages;

    @Column(name = "Poids")
    private String weight;

    @Column(name = "URL")
    private String url;

    @Column(name="coverSmall")
    private String coverSmall;

    @Column(name = "coverMedium")
    private String coverMedium;

    @Column(name = "coverLarge")
    private String coverLarge;

    @ElementCollection
    private List<String> authors;

    @ElementCollection
    private List<String> subjects;

    @ElementCollection
    private Map<String, String> identifiers;

    @ElementCollection
    private List<String> links;
}
