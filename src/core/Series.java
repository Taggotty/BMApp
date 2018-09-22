package core;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Series {

    private ArrayList<Book> books = new ArrayList<>();
    private String name;

    public Series(String name) {
        this.name = name;
    }

    public List<String> getAuthors() {
        return books.stream().map(Book::getName).collect(Collectors.toList());
    }

    public boolean contains(Book book) {
        return books.contains(book);
    }

    public void addBook(Book book) {
        if(!contains(book)) {
            books.add(book);
            book.setSeries(this);
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Series series = (Series) o;

        if (!books.equals(series.books)) return false;
        return name.equals(series.name);
    }

    @Override
    public int hashCode() {
        int result = books.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
