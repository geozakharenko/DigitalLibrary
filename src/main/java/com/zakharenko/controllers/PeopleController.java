package com.zakharenko.controllers;

import com.zakharenko.DAO.PersonDAO;
import com.zakharenko.models.Person;
import com.zakharenko.util.PersonCreateValidator;
import com.zakharenko.util.PersonUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    public final PersonDAO personDao;
    private final PersonUpdateValidator personUpdateValidator;
    private final PersonCreateValidator personCreateValidator;

    @Autowired
    public PeopleController(PersonDAO personDao, PersonUpdateValidator personUpdateValidator, PersonCreateValidator personCreateValidator) {
        this.personDao = personDao;
        this.personUpdateValidator = personUpdateValidator;
        this.personCreateValidator = personCreateValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDao.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.show(id));
        model.addAttribute("books", personDao.getBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personCreateValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String edit(@PathVariable("personId") int personId, Model model) {
        model.addAttribute("person", personDao.show(personId));
        return "people/edit";
    }

    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("personId") int personId) {
        personUpdateValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        personDao.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }
}
