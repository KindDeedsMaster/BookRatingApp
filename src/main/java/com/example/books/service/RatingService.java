package com.example.books.service;

import com.example.books.entity.Book;
import com.example.books.entity.Rating;
import com.example.books.repository.BookRepository;
import com.example.books.repository.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final BookService bookService;
    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;

    public Book rateBook(UUID bookId, int rating) {
        Book book = bookService.getBookById(bookId);
        Rating r = new Rating(book, rating);
        ratingRepository.save(r);
        changeAverageRating(book);
        return book;
    }

    private void changeAverageRating(Book book) {
        List<Rating> ratings = book.getRatings();
        double averageRating = ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0.0);
        book.setAverageRating(averageRating);
        bookRepository.save(book);
    }
}