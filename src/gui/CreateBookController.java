package gui;

import core.Book;
import core.Series;
import core.SeriesContainer;
import core.SingleSeries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.Util;

import java.util.Arrays;
import java.util.Collection;

public class CreateBookController implements Controller {

    private Series series;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfPublisher;
    @FXML
    private TextArea taContent;
    @FXML
    private ComboBox<Series> cbSeries;

    public CreateBookController(Series series) {
        this.series = series;
    }

    @FXML
    protected void initialize() {
        cbSeries.getItems().addAll(SeriesContainer.getInstance().getSeries());
        if (series != null) {
            cbSeries.getSelectionModel().select(series);
            tfAuthor.setText(series.getAuthors().toString());
            tfPublisher.setText(series.getPublisher());

        } else
            cbSeries.getSelectionModel().select(SingleSeries.getInstance());


    }

    @FXML
    public void onCancel(ActionEvent event) {
        Util.cancel(event);
    }

    @FXML
    public void onCreate(ActionEvent event) {
        String name = tfName.getText();
        String publisher = tfPublisher.getText();
        Collection<String> authors = Arrays.asList(tfAuthor.getText().split(";"));
        String content = taContent.getText();
        new Book(name, publisher, content, authors, cbSeries.getValue());
        Util.cancel(event);
    }

    public enum Create {
        BOOK, SERIES
    }

}
