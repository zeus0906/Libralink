package com.api.gesboo.service;

import com.api.gesboo.GoogleBookApi.GoogleBooksResponse;
import com.api.gesboo.entite.Book;
import com.api.gesboo.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class GoogleBookService {

    private static final String URI_API= "https://openlibrary.org/api/books";

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    BookRepository bookRepository;

    public void saveBookFromGoogleBook(String isbn) {
        Book book = searchBookByISBN(isbn);
        bookRepository.save(book);
    }

    public Book searchBookByISBN(String isbn) {
        String url = URI_API + "?bibkeys=ISBN:" + isbn + "&jscmd=data&format=json";
        GoogleBooksResponse response = restTemplate.getForObject(url, GoogleBooksResponse.class);

        if (response != null && response.getTotalItems() > 0) {
            return response.getItems().get(0).getBook();
        }
        return null;
    }


}


