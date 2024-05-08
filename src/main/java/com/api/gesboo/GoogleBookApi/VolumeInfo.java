package com.api.gesboo.GoogleBookApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo {

    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private String language;
    private int pageCount;
    private ImageLinks imageLinks; // Information pour récuperer l'image de couverture
    private String printType;
    private List<IndustryIdentifier> industryIdentifiers;
    private String previewLink;
    private double averageRating;
    private int ratingsCount;
    private String contentVersion;
    private Dimensions dimensions;
    private String mainCategory;

}
