package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class SingleSeries extends Series {

    private static SingleSeries instance = new SingleSeries();


    private SingleSeries() {
        super("Einzelne Bücher");

    }

    public static SingleSeries getInstance() {
        return instance;
    }

    @Override
    public List<String> getAuthors() {
        List<String> author = new ArrayList<String>();
        author.add("");
        return author;
    }
}
