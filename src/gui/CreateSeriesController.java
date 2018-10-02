package gui;

import util.Util;
import core.Series;
import core.SeriesContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.Collection;

public class CreateSeriesController implements Controller {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPublisher;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextArea taContent;

    @FXML
    protected void initialize() {
    }

    @FXML
    protected void onCreate(ActionEvent event) {
        String name = tfName.getText();
        String publisher = tfPublisher.getText();
        Collection<String> authors = Arrays.asList(tfAuthor.getText().split(";"));
        String content = taContent.getText();
        Series series = new Series(name, publisher, content, authors);
        SeriesContainer.getInstance().addSeries(series);
        Util.cancel(event);
    }

    @FXML
    protected void onCancel(ActionEvent event) {
        Util.cancel(event);
    }

}
