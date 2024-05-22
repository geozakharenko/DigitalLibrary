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
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int personId) {
        return jdbcTemplate.query(
                "SELECT title FROM person JOIN book on person.person_id = book.person_id WHERE book.person_id=?",
                new Object[]{personId},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO Person(full_name, year_of_birth) VALUES (?, ?)",
                person.getFullName(), person.getYearOfBirth()
        );
    }

    public void update(int personId, Person updatedPerson) {
        jdbcTemplate.update(
                "UPDATE Person SET full_name=?, year_of_birth=? WHERE person_id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), personId
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
}
