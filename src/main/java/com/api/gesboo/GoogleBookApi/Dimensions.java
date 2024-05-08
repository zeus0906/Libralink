package com.api.gesboo.GoogleBookApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dimensions {

    @JsonProperty("height")
    private String height;

    @JsonProperty("width")
    private String width;

    @JsonProperty("thickness")
    private String thickness;
}
