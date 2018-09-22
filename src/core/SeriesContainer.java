package core;

import java.util.ArrayList;

public class SeriesContainer {
    private ArrayList<Series> series = new ArrayList<>();

    private static SeriesContainer ourInstance = new SeriesContainer();

    public static SeriesContainer getInstance() {
        return ourInstance;
    }

    private SeriesContainer() {
    }

    public void addSeries(Series series) {
        if(!this.series.contains(series)){
            this.series.add(series);
        }
    }
}
