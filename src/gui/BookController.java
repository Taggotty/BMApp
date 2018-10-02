package gui;

import core.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import services.Share.TODOConnection;
import util.Util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class BookController implements PropertyChangeListener, Controller, DataCreator {
    public Object selected;
    private SeriesContainer seriesContainer;
    @FXML
    private TreeView<Object> dataView;
    @FXML
    private ViewController viewController;

    @FXML
    public void onCreateBook() {
        if (selected instanceof Series)
            onCreateBook((Series) selected);
        else onCreateBook(null);
    }

    @FXML
    @Override
    public void onCreateSingleBook() {
        onCreateBook(SingleSeries.getInstance());
    }

    @FXML
    @Override
    public void onCreateSeries() {
        URL resource = getClass().getResource("CreateSeriesController.fxml");
        Util.createDialog("Create new Series", resource);
    }

    @Override
    public void onCreateBook(Series s) {
        URL resource = getClass().getResource("CreateBookController.fxml");
        CreateBookController controller = new CreateBookController(s);
        Util.createDialogWithController("Create new Book", resource, controller);
    }

    @FXML
    public void onConnect() {
        TODOConnection.getInstance().authorize();
    }

    @FXML
    public void initialize() {
        seriesContainer = SeriesContainer.getInstance();
        seriesContainer.addPropertyChangeListener(this);
        for (Series s : seriesContainer.getSeries()) {
            s.addPropertyChangeListener(this);
        }
        dataView.setRoot(new TreeItem<>("root"));
        setUpTreeView();
        dataView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selected = newValue.getValue();
            if (newValue.getValue() instanceof Book) {
                viewController.setBook((Book) selected);
            }
        });
    }

    private void setUpTreeView() {
        for (Series s : seriesContainer.getSeries()) {
            if (s.equals(SingleSeries.getInstance())) {
                handleListSingleBooks(s);
            } else {
                handleListSeriesBooks(s);
            }
        }
    }

    private void handleListSingleBooks(Series s) {
        TreeItem<Object> sItem = dataView.getRoot();
        for (Book b : s.getBooks()) {
            TreeItem<Object> bItem = new TreeItem<>(b);
            sItem.getChildren().add(bItem);
        }
    }

    private void handleListSeriesBooks(Series s) {
        TreeItem<Object> sItem = new TreeItem<>(s);
        for (Book b : s.getBooks()) {
            TreeItem<Object> bItem = new TreeItem<>(b);
            sItem.getChildren().add(bItem);
        }
        dataView.getRoot().getChildren().add(sItem);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object s = evt.getSource();
        if (s == seriesContainer) {
            ((Series) evt.getNewValue()).addPropertyChangeListener(this);
            setUpTreeView();
        } else if (s instanceof Series) {
            TreeItem<Object> tiSeries = dataView.getRoot();
            if(!s.getClass().equals(SingleSeries.class)) {
                FilteredList<TreeItem<Object>> filteredList = dataView.getRoot().getChildren().filtered(ti -> ti.getValue().equals(s));
                assert filteredList.size() == 1;
                tiSeries = filteredList.get(0);
            }
            switch (evt.getPropertyName()) {
                case "addBook":
                    tiSeries.getChildren().add(new TreeItem<>(evt.getNewValue()));
                    dataView.refresh();
                    break;
                case "removeBook":
                    tiSeries.getChildren().removeIf(ti -> ti.getValue().equals(evt.getNewValue()));
                    break;
            }
        }
    }
}

