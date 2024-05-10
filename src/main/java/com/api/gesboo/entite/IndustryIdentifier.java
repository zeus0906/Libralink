package com.api.gesboo.entite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity@JsonIgnoreProperties(ignoreUnknown = true)
public class IndustryIdentifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long industryId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("identifier")
    private String identifier;
}
