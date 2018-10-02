package gui;

import core.Book;
import core.Series;
import core.SeriesContainer;
import javafx.fxml.FXML;
import util.Util;

import java.net.URL;
import java.util.logging.Level;

import static util.Util.logger;

public abstract class DataManager {
    @FXML
    abstract void onCreateBook();
    @FXML
    abstract void onDelete();

    void onCreateBook(Series s) {
        URL resource = getClass().getResource("CreateBookController.fxml");
        CreateBookController controller = new CreateBookController(s);
        Util.createDialogWithController("Create new Book", resource, controller);
    }
    @FXML
    void onCreateSeries() {
        URL resource = getClass().getResource("CreateSeriesController.fxml");
        Util.createDialog("Create new Series", resource);
    }

    void onDeleteBook(Book book) {
        book.getSeries().removeBook(book);
        logger.log(Level.INFO, "Deleted Book {0} successfully", book);
    }

    void onDeleteSeries(Series series) {
        SeriesContainer.getInstance().remove(series);
        logger.log(Level.INFO, "Deleted Series {0} successfully", series);
    }
}
