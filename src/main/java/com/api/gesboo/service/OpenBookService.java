package com.api.gesboo.service;

import com.api.gesboo.entite.Book;
import com.api.gesboo.repository.BookRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OpenBookService {

    private static final String OPEN_LIBRARY_API_URL= "https://openlibrary.org/api/books";

    private RestTemplate restTemplate = new RestTemplate();

    private final Gson gson;

    @Autowired
    private BookRepository bookRepository;

    public JsonObject getBookByISBN(String isbn) {
        String url = OPEN_LIBRARY_API_URL + "?bibkeys=ISBN:" + isbn + "&jscmd=details&format=json";
        String response = restTemplate.getForObject(url, String.class);

        // Convert response to JsonObject
        JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
        return jsonResponse.getAsJsonObject("ISBN:" + isbn);
    }

    public Book saveBookDetails(String isbn) {
        JsonObject bookJson = getBookByISBN(isbn);

        Book book = new Book();
        book.setIsbn(isbn);

        if (bookJson != null) {
            JsonObject details = bookJson.getAsJsonObject("details");

            book.setTitle(getJsonElementAsString(details, "title"));
            book.setSubtitle(getJsonElementAsString(details, "subtitle"));
            book.setByStatement(getJsonElementAsString(details, "by_statement"));
            book.setPublishDate(getJsonElementAsString(details, "publish_date"));

            JsonArray publishersArray = details.getAsJsonArray("publishers");
            if (publishersArray != null && !publishersArray.isEmpty()) {
                book.setPublishers(publishersArray.get(0).getAsString());
            }

            JsonElement numberOfPagesElement = details.get("number_of_pages");
            if (numberOfPagesElement != null) {
                book.setNumberOfPages(numberOfPagesElement.getAsInt());
            }

            List<String> authors = new ArrayList<>();
            JsonArray authorsArray = details.getAsJsonArray("authors");
            if (authorsArray != null) {
                for (JsonElement authorElement : authorsArray) {
                    JsonObject authorObject = authorElement.getAsJsonObject();
                    authors.add(getJsonElementAsString(authorObject, "name"));
                }
            }
            book.setAuthors(authors);
        }

        return bookRepository.save(book);
    }

    private String getJsonElementAsString(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);
        return element != null ? element.getAsString() : null;
    }
}


