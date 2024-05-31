package com.api.gesboo.GoogleBookApi;

import com.api.gesboo.entite.Book.Book;
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
    private Book book;
}
