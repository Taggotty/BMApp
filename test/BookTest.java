import core.Book;
import core.Ownership;
import core.Series;
import core.SingleSeries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookTest {

    private Series series;

    @BeforeEach
    void init() {
        series = new Series("Warrior Cats", "BELTZ & Gelberg", "Warrior Cats Content", Collections.singleton("Erin Hunter"));
    }

    @Test
    @DisplayName("Create Single Book")
    void createSingleBookTest() {
        // given
        String name = "Verwandlungen";
        Collection<String> authors = Collections.singleton("Ovid");
        String publisher = "Reclam";
        String content = name + " content";

        // when
        Book book = new Book(name, publisher, content, authors);

        //then
        assertEquals(name, book.getName());
        assertEquals(authors, book.getAuthors());
        assertEquals(publisher, book.getPublisher());
        assertEquals(content, book.getContent());
        assertEquals(false, book.isRead());
        assertNotNull(book.getOwnership());
        assertEquals(SingleSeries.getInstance(), book.getSeries());
    }

    @Test
    @DisplayName("Create Book with Series and Author")
    void createBookWithSeriesAndAuthorTest() {
        // given
        String name = "In die Wildnis";
        Collection<String> authors = Collections.singleton("Erin Hunter");
        String publisher = "BELTZ & Gelberg";
        String content = name + " content";

        // when
        Book book = new Book(name, publisher, content, authors, series);

        // then
        assertEquals(name, book.getName());
        assertEquals(authors, book.getAuthors());
        assertEquals(publisher, book.getPublisher());
        assertEquals(content, book.getContent());
        assertEquals(false, book.isRead());
        assertNotNull(book.getOwnership());
        assertEquals(series, book.getSeries());
    }

    @Test
    @DisplayName("Create Book with Series")
    void createBookWithSeriesTest() {
        // given
        String name = "In die Wildnis";

        // when
        Book book = new Book(name, series);

        //then
        assertEquals(series.getPublisher(), book.getPublisher());
        assertEquals(series.getAuthors(), book.getAuthors());
        assertNotEquals(series.getContent(), book.getContent());
        assertEquals(false, book.isRead());
        assertNotNull(book.getOwnership());

    }

    @Test
    @DisplayName("Create Book with Series and Content")
    void createBookWithSeriesAndContentTest() {
        // given
        String name = "In die Wildnis";
        String content = name + " content";

        // when
        Book book = new Book(name, content, series);

        //then
        assertEquals(name, book.getName());
        assertEquals(series.getPublisher(), book.getPublisher());
        assertEquals(series.getAuthors(), book.getAuthors());
        assertEquals(content, book.getContent());
        assertNotNull(book.getOwnership());
    }

    @Test
    @DisplayName("Change Series of Book")
    void changeSeriesOfBookTest(){
        // given
        String name = "In die Wildnis";
        Collection<String> authors = Collections.singleton("Erin Hunter");
        String publisher = "BELTZ & Gelberg";
        String content = name + " content";
        Book book = new Book(name, publisher, content, authors, SingleSeries.getInstance());

        // when
        book.setSeries(series);

        // then
        assertEquals(name, book.getName());
        assertEquals(authors, book.getAuthors());
        assertEquals(publisher, book.getPublisher());
        assertEquals(content, book.getContent());
        assertEquals(series, book.getSeries());
        assertNotNull(book.getOwnership());
    }
}
