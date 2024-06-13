package com.zakharenko.util;

import com.zakharenko.models.Book;
import com.zakharenko.services.BooksService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookCreateValidator implements Validator {
    private final BooksService booksService;

    public BookCreateValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (booksService.findByAuthorAndTitle(book.getAuthor(), book.getTitle()).isPresent())
            errors.rejectValue("title", "", "Такая книга уже существует");
    }
}
