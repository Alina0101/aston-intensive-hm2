package entities;

public class Book {
    private Long id;
    private String title;
    private Long authorId;
    private int publishedYear;
    private String genre;

    public Book() {
    }

    public Book(Long id, String title, Long authorId, int publishedYear, String genre) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.publishedYear = publishedYear;
        this.genre = genre;
    }

    public Book(String title, Long authorId, int publishedYear, String genre) {
        this.title = title;
        this.authorId = authorId;
        this.publishedYear = publishedYear;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", publishedYear=" + publishedYear +
                ", genre='" + genre + '\'' +
                '}';
    }
}