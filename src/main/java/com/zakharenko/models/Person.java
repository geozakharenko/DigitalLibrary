package com.zakharenko.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(min = 5, max = 120, message = "ФИО должно иметь длину 5-120 символов")
    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+", message = "ФИО должно иметь следующий формат:" +
            "Фамилия Имя Отчество")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "Ваш год рождения не должен быть раньше 1900")
    @Max(value = 2100, message = "Ваш год рождения не должен превышать 2100")
    @NotNull(message = "Год рождения не должен быть пустым")
    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String fullName, Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        this.fullName = fullName;
    }

    public Person() {
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", fullName='" + fullName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}
