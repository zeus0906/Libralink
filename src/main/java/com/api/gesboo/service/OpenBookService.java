package com.api.gesboo.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class OpenBookService {

    private static final String OPEN_LIBRARY_API_URL= "https://openlibrary.org/api/books";

    private RestTemplate restTemplate = new RestTemplate();

    private final Gson gson;

    public JsonObject getBookByISBN(String isbn) {
        String url = OPEN_LIBRARY_API_URL + "?bibkeys=ISBN:" + isbn + "&jscmd=details&format=json";
        String response = restTemplate.getForObject(url, String.class);

        // Convert response to JsonObject
        JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
        return jsonResponse.getAsJsonObject("ISBN:" + isbn);
    }


}


