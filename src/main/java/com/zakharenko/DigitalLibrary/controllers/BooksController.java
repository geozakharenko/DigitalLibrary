package com.zakharenko.DigitalLibrary.controllers;

import com.zakharenko.DigitalLibrary.models.Book;
import com.zakharenko.DigitalLibrary.models.Person;
import com.zakharenko.DigitalLibrary.services.BooksService;
import com.zakharenko.DigitalLibrary.services.PeopleService;
import com.zakharenko.DigitalLibrary.util.BookCreateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear,
                        Model model) {
        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sortByYear));
        else {
            Page<Book> usersPage = booksService.findAll(page, booksPerPage, sortByYear);
            model.addAttribute("books", usersPage.getContent());
            model.addAttribute("totalPages", usersPage.getTotalPages());
            model.addAttribute("books_per_page", booksPerPage);
            model.addAttribute("sort_by_year", sortByYear);
        }
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

    @GetMapping("/search")
    public String search(@ModelAttribute("book") Book book,
                         @RequestParam(value = "request", required = false) String request,
                         Model model) {
        if (request != null)
            model.addAttribute("foundBooks", booksService.findByTitleStartingWith(request));
        return "books/search";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("book") Book book) throws UnsupportedEncodingException {
        if (Objects.equals(book.getTitle(), "")) return "redirect:/books/search";
        else {
            String encodedTitle = URLEncoder.encode(book.getTitle(), StandardCharsets.UTF_8);
            return "redirect:/books/search?request=" + encodedTitle;
        }
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
        return "redirect:/books/" + bookId;
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
