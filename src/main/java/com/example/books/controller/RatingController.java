package com.example.books.controller;

import com.example.books.entity.Book;
import com.example.books.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/books")
public class RatingController {
    private final RatingService ratingService;

    @PostMapping(path = "/{id}/rate")
    public ResponseEntity<Book> rateBook (@PathVariable UUID id,
                                          @RequestParam int rating){
        Book book = ratingService.rateBook(id, rating);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
