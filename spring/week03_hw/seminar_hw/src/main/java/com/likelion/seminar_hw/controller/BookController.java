package com.likelion.seminar_hw.controller;

import com.likelion.seminar_hw.model.Book;
import com.likelion.seminar_hw.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

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
}