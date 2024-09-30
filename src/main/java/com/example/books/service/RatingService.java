package com.example.books.service;

import com.example.books.entity.Book;
import com.example.books.entity.Rating;
import com.example.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final BookService bookService;
    private final BookRepository bookRepository;

    public Book rateBook(UUID bookId, int rating) {
        Book book = bookService.getBookById(bookId);
        Rating r = Rating.builder()
                .book(book)
                .rating(rating)
                .build();
        book.addRating(r);
        bookRepository.save(book);
        return book;
    }
}