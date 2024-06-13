package com.zakharenko.controllers;

import com.zakharenko.models.Book;
import com.zakharenko.models.Person;
import com.zakharenko.util.BookCreateValidator;
import com.zakharenko.services.BooksService;
import com.zakharenko.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookCreateValidator bookCreateValidator;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookCreateValidator bookCreateValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookCreateValidator = bookCreateValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String show(@ModelAttribute("person") Person person,
                       @PathVariable("bookId") int bookId, Model model) {
        model.addAttribute("book", booksService.findOne(bookId));
        model.addAttribute("receivedPerson", booksService.getBookOwner(bookId));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookCreateValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", booksService.findOne(bookId));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int bookId) {
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(bookId, book);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/removePerson")
    public String release(@PathVariable("bookId") int bookId) {
        System.out.println(bookId);
        booksService.release(bookId);
        return "redirect:/books/"+bookId;
    }

    @PatchMapping("/{bookId}/assignPerson")
    public String assignAPerson(@ModelAttribute("person") Person person,
                                @PathVariable("bookId") int bookId) {
        booksService.assignAPerson(person, bookId);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        booksService.delete(bookId);
        return "redirect:/books";
    }
}
