package core;

import data.FileIO;
import util.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SeriesContainer extends ChangeSupport<Series> implements Serializable {


    private static final long serialVersionUID = 5969409675411119342L;
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

            // as SingleSeries is a singleton the instance has to be initialized with the value of the serialized SingleSeries
            List<Series> singleSeries = ourInstance.getSeries().stream().filter(series -> series.getClass().equals(SingleSeries.class)).collect(Collectors.toList());
            if (singleSeries.size() == 1)
                SingleSeries.setInstance((SingleSeries) singleSeries.get(0));
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

    public void remove(Series series) {
        this.series.remove(series);
    }

    /**
     * Just used for testing!
     */
    public void removeAll() {
        this.series.clear();
    }
}
