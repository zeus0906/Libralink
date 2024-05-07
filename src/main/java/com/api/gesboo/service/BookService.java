package com.api.gesboo.service;

import com.api.gesboo.entite.GoogleBooksResponse;
import com.api.gesboo.entite.VolumeInfo;
import com.api.gesboo.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class BookService {

    private static final String URI_API= "https://www.googleapis.com/books/v1/volumes";
    private static final String URI_KEY = "AIzaSyDCQqKd_XAul5J9ZC3IOveWm_qnuIIMBBc";


    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    BookRepository bookRepository;

    public VolumeInfo searchBookByISBN(String isbn) {
        String url = URI_API + "?q=isbn:" + isbn + "&key=" + URI_KEY;
        GoogleBooksResponse response = restTemplate.getForObject(url, GoogleBooksResponse.class);
        if (response != null && response.getTotalItems() > 0) {
            return response.getItems().get(0).getVolumeInfo();
        }
        return null;
    }


}
