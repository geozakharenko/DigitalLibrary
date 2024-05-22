package com.zakharenko.models;

import javax.validation.constraints.*;

public class Person {
    private int personId;

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(min = 5, max = 120, message = "ФИО должно иметь длину 5-120 символов")
    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+", message = "ФИО должно иметь следующий формат:" +
    "Фамилия Имя Отчество")
    private String fullName;

    @Min(value = 1900, message = "Ваш год рождения не должен быть раньше 1900")
    @Max(value = 2100, message = "Ваш год рождения не должен превышать 2100")
    @NotNull(message = "Год рождения не должен быть пустым")
    private Integer yearOfBirth;

    public Person(int personId, String fullName, Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        this.fullName = fullName;
        this.personId = personId;
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
}
