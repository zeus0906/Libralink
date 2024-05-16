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

    private String isbn;
    private String title;
    private String subtitle;
    private String byStatement;
    private String publishDate;
    private String publishers;
    private int numberOfPages;
    private String weight;
    private String url;
    private String coverSmall;
    private String coverMedium;
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
