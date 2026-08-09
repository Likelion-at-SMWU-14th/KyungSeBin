package com.likelion.seminar_hw.service;

import com.likelion.seminar_hw.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();

    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    public List<Book> findAll() {
        return books;
    }
}