package com.api.gesboo.GoogleBookApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageLinks {
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

}
