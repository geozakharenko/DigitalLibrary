package com.zakharenko.controllers;

import com.zakharenko.DAO.BookDAO;
import com.zakharenko.models.Book;
import com.zakharenko.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    public final BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String show(@ModelAttribute("person") Person person,
            @PathVariable("bookId") int bookId, Model model) {
        model.addAttribute("book", bookDAO.show(bookId));
        model.addAttribute("receivedPerson", bookDAO.getPersonFromBookId(bookId));
        model.addAttribute("people", bookDAO.getAllPersons());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookDAO.show(bookId));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int bookId) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookDAO.update(bookId, book);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/removePerson")
    public String deletePersonDependency(@PathVariable("bookId") int bookId) {
        bookDAO.deletePersonDependency(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/assignPerson")
    public String assignAPerson(@ModelAttribute("person") Person person,
            @PathVariable("bookId") int bookId) {
        bookDAO.assignAPerson(person ,bookId);
        return "redirect:/books";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        bookDAO.delete(bookId);
        return "redirect:/books";
    }
}
