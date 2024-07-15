package com.zakharenko.DigitalLibrary.util;

import com.zakharenko.DigitalLibrary.models.Person;
import com.zakharenko.DigitalLibrary.services.PeopleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonCreateValidator implements Validator {
    private final PeopleService peopleService;

    public PersonCreateValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (peopleService.findByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Это имя уже используется");
    }
}
