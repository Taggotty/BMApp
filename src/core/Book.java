package core;

import services.Share.Taskable;

import java.util.Collection;

public class Book extends Readable implements Taskable {

    private Series series;
    private Ownership ownership = new Ownership();

    public Book(String name, String publisher, String content, Collection<String> authors, Series series) {
        super(name, publisher, content, authors);
        setSeries(series);
    }

    public Book(String name, String publisher, String content, Collection<String> authors) {
        this(name, publisher, content, authors, SingleSeries.getInstance());
    }

    public Book(String name, String content, Series series) {
        this(name, series.getPublisher(), content, series.getAuthors(), series);
    }

    public Book(String name, Series series) {
        this(name, null, series);
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        if (!series.equals(this.series)) {
            this.series = series;
            series.addBook(this);
        }
    }

    public Ownership getOwnership() {
        return ownership;
    }

    public void setOwnershipTo(Ownership ownership) {
        this.ownership = ownership;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!super.equals(book)) return false;
        return series != null ? series.equals(book.series) : book.series == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (series != null ? series.hashCode() : 0);
        return result;
    }

    @Override
    public void authorize() {
    }

    @Override
    public void createTask() {

    }
}
