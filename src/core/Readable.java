package core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Readable extends ChangeSupport implements Serializable {

    static final long serialVersionUID = -156184637345499177L;
    private transient java.beans.PropertyChangeSupport pcs;

    private Boolean isRead;
    private String name;
    private Set<String> authors = new HashSet<String>() {
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (String author : authors)
                sb.append(author).append(";");
            return sb.toString();
        }
    };
    private String publisher;
    private String content;

    public Readable(String name, String publisher, String content, Collection<String> authors) {
        this.name = name;
        this.publisher = publisher;
        this.content = content;
        this.isRead = false;
        addAuthors(authors);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getAuthors() {
        return authors;
    }

    public void addAuthor(String author) {
        this.authors.add(author);
    }

    public void addAuthors(Collection<String> authors) {
        this.authors.addAll(authors);
    }

    public void removeAuthors(Collection<String> authors) {
        this.authors.removeAll(authors);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Readable readable = (Readable) o;

        if (!name.equals(readable.name)) return false;
        if (!authors.equals(readable.authors)) return false;
        if (publisher != null ? !publisher.equals(readable.publisher) : readable.publisher != null) return false;
        return content != null ? content.equals(readable.content) : readable.content == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + authors.hashCode();
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
