package com.zakharenko.DAO;

import com.zakharenko.models.Book;
import com.zakharenko.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }


    public Book show(int bookId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[]{bookId},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public Optional<Book> checkSameBook(String author, String title) {
        return jdbcTemplate.query(
                "SELECT * FROM book WHERE author=? AND title=?",
                new Object[]{author, title},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Person getPersonFromBookId(int bookId) {
        return jdbcTemplate.query("SELECT full_name FROM book JOIN person on person.person_id = book.person_id WHERE book_id = ?",
                new Object[]{bookId},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public List<Person> getAllPersons() {
        return jdbcTemplate.query(
                "SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Book book) {
        jdbcTemplate.update(
                "INSERT INTO Book(title, author, year_of_release) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYearOfRelease()
        );
    }

    public void update(int bookId, Book updatedBook) {
        jdbcTemplate.update(
                "UPDATE Book SET title=?, author=?, year_of_release=? WHERE book_id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYearOfRelease(), bookId
        );
    }

    public void assignAPerson(Person newPerson, int bookId) {
        jdbcTemplate.update(
                "UPDATE book SET person_id=? WHERE book_id=?;",
                newPerson.getPersonId(), bookId
        );
    }

    public void deletePersonDependency(int bookId) {
        jdbcTemplate.update(
                "UPDATE book SET person_id=NULL WHERE book_id=?", bookId
        );
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", bookId);
    }

}
