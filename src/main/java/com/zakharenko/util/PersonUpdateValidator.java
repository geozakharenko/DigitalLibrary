package com.zakharenko.util;

import com.zakharenko.DAO.PersonDAO;
import com.zakharenko.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonUpdateValidator implements Validator {
    private final PersonDAO personDao;

    public PersonUpdateValidator(PersonDAO personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        //Проверка на равенство полученного из модели имени с именем искомым
        if (!(personDao.show(person.getPersonId()).getFullName().equals(person.getFullName())) &&
                personDao.show(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Это имя уже используется");
    }
}
