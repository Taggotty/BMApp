package core;

public class Book {

    private String name;
    private String author;
    private String content;
    private Series series;

    public Book(String name, String author, Series series) {
        this.name = name;
        this.author = author;
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        if(!series.equals(this.series)) {
            this.series = series;
            series.addBook(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!name.equals(book.name)) return false;
        if (!author.equals(book.author)) return false;
        if (content != null ? !content.equals(book.content) : book.content != null) return false;
        return series != null ? series.equals(book.series) : book.series == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (series != null ? series.hashCode() : 0);
        return result;
    }
}
