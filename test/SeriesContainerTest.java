import core.Series;
import core.SeriesContainer;
import core.SingleSeries;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeriesContainerTest {

    SeriesContainer container = SeriesContainer.getInstance();

    @BeforeEach
    void reinit(){
        container.removeAll();
        container.addSeries(SingleSeries.getInstance());
    }

    @Test
    void addSeriesToContainer() {
        // given
        String name = "Warrior Cats";
        String publisher = "BELTZ & Gelberg";
        String content = name + "content";
        Collection<String> author = Collections.singleton("Erin Hunter");
        Series series = new Series(name, publisher, content, author);

        // when
        container.addSeries(series);

        // then
        assertTrue(container.getSeries().contains(series));
    }

    @Test
    void addSeriesToContainerTwice() {
        // when
        container.addSeries(SingleSeries.getInstance());

        // then
        assertTrue(container.getSeries().contains(SingleSeries.getInstance()));
        assertEquals(1, container.getSeries().size());
    }
}
