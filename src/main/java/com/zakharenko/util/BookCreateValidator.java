package com.zakharenko.util;

import com.zakharenko.DAO.BookDAO;
import com.zakharenko.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookCreateValidator implements Validator {
    private final BookDAO bookDAO;

    public BookCreateValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (bookDAO.checkSameBook(book.getAuthor(), book.getTitle()).isPresent())
            errors.rejectValue("title", "", "Такая книга уже существует");
    }
}
