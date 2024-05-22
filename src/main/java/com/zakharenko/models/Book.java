package com.zakharenko.models;

import javax.validation.constraints.*;

public class Book {
    private int bookId;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 1, max = 240, message = "Название книги не должно быть короче 1 или превышать 240 символов")
    private String title;

    @NotEmpty(message = "Поле \"Автор книги\" не должно быть пустым")
    @Size(max = 120, message = "Поле \"Автор книги\" не должно превышать 120 символов")
    @Pattern(regexp = "(\\p{Lu}\\p{Ll}*\\s)+(\\p{Lu}\\p{Ll}*)",
            message = "Поле \"Автор книги\" должно иметь формат Xxx Xxx ...[Xxx] или X X ...[X]")
    private String author;


    @Min(value = 0, message = "Год выпуска книги не должен быть раньше нашей эры")
    @Max(value = 2100, message = "Год выпуска книги не должен превышать 2100")
    @NotNull(message = "Год выпуска книги не должен быть пустым")
    private Integer yearOfRelease;

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

}
