package com.zakharenko.services;

import com.zakharenko.models.Book;
import com.zakharenko.models.Person;
import com.zakharenko.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        book.setBookId(id);
        book.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getBookOwner(int bookId) {
        return booksRepository.findById(bookId).map(Book::getOwner).orElse(null);
    }

    public Optional<Book> findByAuthorAndTitle(String author, String title) {
        return booksRepository.findByAuthorAndTitle(author, title).stream().findAny();
    }

    @Transactional
    public void release(int bookId) {
        booksRepository.findById(bookId).ifPresent(
                book -> book.setOwner(null)
        );
    }

    @Transactional
    public void assignAPerson(Person newPerson, int bookId) {
        booksRepository.findById(bookId).ifPresent(
                book -> book.setOwner(newPerson)
        );
    }
}
