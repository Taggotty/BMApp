package gui;

import core.Book;
import core.Series;
import core.SeriesContainer;
import core.SingleSeries;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import services.Share.TODOConnection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BookController extends DataManager implements PropertyChangeListener, Controller {
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
    public void onDelete() {
        if(selected instanceof Series)
            onDeleteSeries((Series) selected);
        else if(selected instanceof Book)
            onDeleteBook((Book) selected);
    }

    @FXML
    public void onCollapseAll() {
        dataView.getRoot().getChildren().forEach(ti -> ti.setExpanded(false));
    }

    @FXML
    public void onExpandAll() {
        dataView.getRoot().getChildren().forEach(ti -> ti.setExpanded(true));
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
            TreeItem<Object> sItem = new TreeItem<>(s);
            for (Book b : s.getBooks()) {
                TreeItem<Object> bItem = new TreeItem<>(b);
                sItem.getChildren().add(bItem);
            }
            dataView.getRoot().getChildren().add(sItem);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object s = evt.getSource();
        if (s == seriesContainer) {
            ((Series) evt.getNewValue()).addPropertyChangeListener(this);
            setUpTreeView();
        } else if (s instanceof Series) {

            FilteredList<TreeItem<Object>> filteredList = dataView.getRoot().getChildren().filtered(ti -> ti.getValue().equals(s));
            assert filteredList.size() == 1;
            TreeItem<Object> tiSeries = filteredList.get(0);
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

