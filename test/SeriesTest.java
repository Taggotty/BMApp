import core.Book;
import core.Series;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeriesTest {

    @Test
    void createSeries(){
        // given
        String name = "Warrior Cats";
        String publisher = "BELTZ & Gelberg";
        String content = name + "content";
        Collection<String> author = Collections.singleton("Erin Hunter");

        // when
        Series series = new Series(name, publisher, content, author);

        // then
        assertEquals(name, series.getName());
        assertEquals(publisher, series.getPublisher());
        assertEquals(content, series.getContent());
        assertEquals(author, series.getAuthors());
        assertEquals(Collections.emptyList(), series.getBooks());
    }
}
