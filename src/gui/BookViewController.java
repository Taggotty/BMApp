package gui;

import core.Book;
import core.Ownership;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class BookViewController {

    Book book;

    @FXML
    private Label lblTitel;
    @FXML
    private Label lblAuthor;
    @FXML
    private Label lblPublisher;
    @FXML
    private Label lblContent;
    @FXML
    private CheckBox cbRead;
    @FXML
    private ChoiceBox<Ownership.State> cbOwnership;
    @FXML
    private TextField tfAn;

    @FXML
    protected void initialize() {
        cbOwnership.getItems().addAll(Ownership.State.values());
        cbOwnership.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            book.getOwnership().setState(newValue, null);
        }));
        cbRead.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            book.setRead(newValue);
        }));
        // tfAn.onKeyPressedProperty().addListener();
    }

    public void setBook(Book book) {
        this.book = book;
        lblTitel.setText(book.getName());
        lblAuthor.setText(book.getAuthors().toString());
        lblPublisher.setText(book.getPublisher());
        lblContent.setText(book.getContent());
        cbRead.setSelected(book.isRead());
        cbOwnership.getSelectionModel().select(book.getOwnership().getState());
        if(book.getOwnership().getLend() != null)
            tfAn.setText(book.getOwnership().getLend());

    }

    public void setRead() {

    }

    public void setOwnership() {

    }
}
