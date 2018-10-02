package gui;

import core.Book;
import core.Ownership;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import util.Util;

import java.awt.event.ActionEvent;
import java.util.logging.Level;

import static util.Util.logger;

public class ViewController {

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
            book.getOwnership().setState(newValue);
        }));
        cbRead.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            book.setRead(newValue);
            logger.log(Level.FINE, "Set read of Book {0} to {1}", new Object[]{book, newValue});
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
        else
            tfAn.setText("");

    }

    @FXML
    public void onLend() {
        book.getOwnership().setLend(tfAn.getText());
    }

    @FXML
    public void onDelete() {
        book.getSeries().removeBook(book);
        logger.log(Level.INFO, "Book removed successfully.");
    }

    public void setRead() {

    }

    public void setOwnership() {

    }
}
