package core;

import data.FileIO;
import util.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;

public class SeriesContainer extends ChangeSupport<Series> implements Serializable {
    private static SeriesContainer ourInstance;
    private ArrayList<Series> series = new ArrayList<>();

    private SeriesContainer() {
        this.addSeries(SingleSeries.getInstance());
    }

    public static SeriesContainer getInstance() {
        if(ourInstance == null)
            ourInstance = new SeriesContainer();
        return ourInstance;
    }

    public static void load() throws IOException {
        if (FileIO.exists(SeriesContainer.class)) {
            Util.logger.log(Level.FINE, "File exists.");
            ourInstance = FileIO.load(SeriesContainer.class);
        }
    }

    public static void save() throws IOException {
        FileIO.save(SeriesContainer.getInstance());
    }

    public void addSeries(Series series) {
        if (!this.series.contains(series)) {
            this.series.add(series);
            fireEvent("addSeries", series);
        }
    }

    public ArrayList<Series> getSeries() {
        return series;
    }

    /**
     * Just used for testing!
     */
    public void removeAll() {
        this.series.clear();
    }
}
