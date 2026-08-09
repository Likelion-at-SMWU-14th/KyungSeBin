package com.likelion.seminar_hw.controller;

import com.likelion.seminar_hw.dto.Book;
import com.likelion.seminar_hw.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //MVC
    @GetMapping("/books")
    public String viewBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @PostMapping("/books")
    public String addBook(Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    //RESTAPI
    @GetMapping("/api/books")
    @ResponseBody
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @PostMapping("/api/books")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBookApi(@RequestBody Book book) {
        return bookService.addBook(book);
    }
}