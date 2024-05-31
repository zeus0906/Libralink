package com.api.gesboo.Test;

import com.api.gesboo.entite.Book;
import com.api.gesboo.entite.Collection;
import com.api.gesboo.entite.CollectionType;
import com.api.gesboo.repository.BookRepository;
import com.api.gesboo.repository.CollectionRepository;
import com.api.gesboo.service.CollectionService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CollectionServiceTest {
    @InjectMocks
    private CollectionService collectionService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CollectionRepository collectionRepository;

    public CollectionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBookToCollection() {
        String isbn = "9789351199250";
        CollectionType collectionType = CollectionType.FAVORITES;

        Book book = new Book();
        book.setIsbn(isbn);

        Collection collection = new Collection();
        collection.setType(collectionType);

        when(bookRepository.findByIsbn(isbn)).thenReturn(book);
        when(collectionRepository.findByType(collectionType)).thenReturn(Optional.of(collection));

        Book result = collectionService.addBookToCollection(isbn, collectionType);

        assertNotNull(result);
        verify(collectionRepository, times(1)).save(any(Collection.class));
    }
}
