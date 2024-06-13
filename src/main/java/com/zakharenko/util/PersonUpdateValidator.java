package com.zakharenko.util;

import com.zakharenko.models.Person;
import com.zakharenko.services.PeopleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonUpdateValidator implements Validator {
    private final PeopleService peopleService;

    public PersonUpdateValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        //Проверка на равенство полученного из модели имени с именем искомым
        if (!(peopleService.findOne(person.getPersonId()).getFullName().equals(person.getFullName())) &&
                peopleService.findByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Это имя уже используется");
    }
}
