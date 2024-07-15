package com.zakharenko.DigitalLibrary.models;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 1, max = 240, message = "Название книги не должно быть короче 1 или превышать 240 символов")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Поле \"Автор книги\" не должно быть пустым")
    @Size(max = 120, message = "Поле \"Автор книги\" не должно превышать 120 символов")
    @Pattern(regexp = "(\\p{Lu}\\p{Ll}*)(\\s\\p{Lu}\\p{Ll}*\\.?)*",
            message = "Поле \"Автор книги\" должно иметь формат \"Иванов Иван Иванович\" или " +
                    "\"Иванов И. И.\"")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "Год выпуска книги не должен быть раньше нашей эры")
    @Max(value = 2100, message = "Год выпуска книги не должен превышать 2100")
    @NotNull(message = "Год выпуска книги не должен быть пустым")
    @Column(name = "year_of_release")
    private Integer yearOfRelease;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Transient
    private boolean expired;

    public Book(String title, String author, Integer yearOfRelease) {
        this.title = title;
        this.author = author;
        this.yearOfRelease = yearOfRelease;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                '}';
    }
}
