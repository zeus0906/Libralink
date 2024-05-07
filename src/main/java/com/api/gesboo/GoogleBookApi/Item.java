package com.api.gesboo.GoogleBookApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {

    private String id;
    private VolumeInfo volumeInfo;
}
