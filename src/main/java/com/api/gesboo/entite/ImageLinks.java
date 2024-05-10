package com.api.gesboo.entite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageLinks {

    @Id
    @GeneratedValue
    private Long idImageLinks;

    @JsonProperty("smallThumbnail")
    private String smallThumbnail;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("small")
    private String small;

    @JsonProperty("medium")
    private String medium;

    @JsonProperty("large")
    private String large;

    @JsonProperty("extraLarge")
    private String extraLarge;
}
