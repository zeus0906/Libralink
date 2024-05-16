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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OpenBookService {

    private static final String OPEN_LIBRARY_API_URL= "https://openlibrary.org/api/books";

    private RestTemplate restTemplate = new RestTemplate();

    private final Gson gson;

    @Autowired
    private BookRepository bookRepository;

    // Permet de récuperer les données sur l'API Open Library et de l'afficher sur format JSON
    public JsonObject getBookByISBN(String isbn) {
        String url = OPEN_LIBRARY_API_URL + "?bibkeys=ISBN:" + isbn + "&jscmd=data&format=json";
        String response = restTemplate.getForObject(url, String.class);

        // Convert response to JsonObject
        JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
        return jsonResponse.getAsJsonObject("ISBN:" + isbn);
    }

    // Permet de faire la sauvegarde des données récuperer dans ma BD
    public Book saveBookDetails(String isbn) {
        // Vérifier si le livre existe déjà dans la base de données
        Book existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook != null) {
            // Si le livre existe déjà, retourner sans sauvegarder
            return existingBook;
        }

        JsonObject bookJson = getBookByISBN(isbn);

        if (bookJson == null || bookJson.isJsonNull()) {
            // Gérer le cas où aucune donnée n'est renvoyée pour cet ISBN
            return null;
        }

        Book book = new Book();
        book.setIsbn(isbn);

        // Vérifier chaque clé avant d'extraire sa valeur
        book.setTitle(getJsonElementAsString(bookJson, "title"));
        book.setSubtitle(getJsonElementAsString(bookJson, "subtitle"));
        book.setByStatement(getJsonElementAsString(bookJson, "by_statement"));
        book.setPublishDate(getJsonElementAsString(bookJson, "publish_date"));
        book.setWeight(getJsonElementAsString(bookJson, "weight"));
        book.setUrl(getJsonElementAsString(bookJson, "url"));

        JsonArray publishersArray = bookJson.getAsJsonArray("publishers");
        if (publishersArray != null && !publishersArray.isEmpty()) {
            JsonObject publisherObject = publishersArray.get(0).getAsJsonObject();
            book.setPublishers(getJsonElementAsString(publisherObject, "name"));
        }

        JsonObject cover = bookJson.getAsJsonObject("cover");
        if (cover != null) {
            book.setCoverSmall(getJsonElementAsString(cover, "small"));
            book.setCoverMedium(getJsonElementAsString(cover, "medium"));
            book.setCoverLarge(getJsonElementAsString(cover, "large"));
        }

        JsonElement numberOfPagesElement = bookJson.get("number_of_pages");
        if (numberOfPagesElement != null && !numberOfPagesElement.isJsonNull()) {
            book.setNumberOfPages(numberOfPagesElement.getAsInt());
        }

        List<String> authors = new ArrayList<>();
        JsonArray authorsArray = bookJson.getAsJsonArray("authors");
        if (authorsArray != null && !authorsArray.isJsonNull()) {
            for (JsonElement authorElement : authorsArray) {
                JsonObject authorObject = authorElement.getAsJsonObject();
                if (authorObject != null && !authorObject.isJsonNull()) {
                    authors.add(getJsonElementAsString(authorObject, "name"));
                }
            }
        }
        book.setAuthors(authors);

        List<String> subjects = new ArrayList<>();
        JsonArray subjectsArray = bookJson.getAsJsonArray("subjects");
        if (subjectsArray != null && !subjectsArray.isJsonNull()) {
            for (JsonElement subjectElement : subjectsArray) {
                JsonObject subjectObject = subjectElement.getAsJsonObject();
                if (subjectObject != null && !subjectObject.isJsonNull()) {
                    subjects.add(getJsonElementAsString(subjectObject, "name"));
                }
            }
        }
        book.setSubjects(subjects);

        Map<String, String> identifiers = new HashMap<>();
        JsonObject identifiersObject = bookJson.getAsJsonObject("identifiers");
        if (identifiersObject != null && !identifiersObject.isJsonNull()) {
            for (Map.Entry<String, JsonElement> entry : identifiersObject.entrySet()) {
                JsonArray idArray = entry.getValue().getAsJsonArray();
                if (idArray != null && !idArray.isEmpty()) {
                    identifiers.put(entry.getKey(), idArray.get(0).getAsString());
                }
            }
        }
        book.setIdentifiers(identifiers);

        List<String> links = new ArrayList<>();
        JsonArray linksArray = bookJson.getAsJsonArray("links");
        if (linksArray != null && !linksArray.isJsonNull()) {
            for (JsonElement linkElement : linksArray) {
                JsonObject linkObject = linkElement.getAsJsonObject();
                if (linkObject != null && !linkObject.isJsonNull()) {
                    links.add(getJsonElementAsString(linkObject, "url"));
                }
            }
        }
        book.setLinks(links);

        return bookRepository.save(book);
    }

    private String getJsonElementAsString(JsonObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            JsonElement element = jsonObject.get(key);
            if (element != null && !element.isJsonNull()) {
                return element.getAsString();
            }
        }
        return null;
    }

    // Permet d'afficher la liste des livres qui se trouve dans la BD
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}


