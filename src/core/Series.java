package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class Series extends Readable {
    private static final long serialVersionUID = -2430599298334797213L;
    private ArrayList<Book> books = new ArrayList<>();

    protected Series(String name) {
        this(name, null, null, Collections.emptySet());
    }

    public Series(String name, String publisher, String content, Collection<String> authors) {
        super(name, publisher, content, authors);
    }

    public boolean contains(Book book) {
        return books.contains(book);
    }

    public void addBook(Book book) {
        if (!contains(book)) {
            books.add(book);
            book.setSeries(this);
            addAuthors(book.getAuthors());
            fireEvent("addBook", book);
        }
    }

    public void removeBook(Book book) {
        if (contains(book)) {
            books.remove(book);
            Set<String> toBeRemovedAuthors = book.getAuthors().stream().filter(author -> books.stream().noneMatch(b -> b.getAuthors().contains(author))).collect(Collectors.toSet());
            removeAuthors(toBeRemovedAuthors);
            fireEvent("removeBook", book);
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
        return super.equals(series);
    }

    @Override
    public int hashCode() {
        int result = books.hashCode();
        result = 31 * result + super.hashCode();
        return result;
    }
}
