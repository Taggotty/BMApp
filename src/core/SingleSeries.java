package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class SingleSeries extends Series {

    private static final long serialVersionUID = -5098927307288598612L;
    private static SingleSeries instance = new SingleSeries();


    private SingleSeries() {
        super("Einzelne Bücher");

    }

    public static SingleSeries getInstance() {
        return instance;
    }

    protected static void setInstance(SingleSeries series){
        instance = series;
    }

    @Override
    public List<String> getAuthors() {
        List<String> author = new ArrayList<String>();
        author.add("");
        return author;
    }
}
