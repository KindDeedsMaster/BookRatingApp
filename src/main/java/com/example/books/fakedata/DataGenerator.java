package com.example.books.fakedata;

import com.example.books.entity.Book;
import com.example.books.repository.BookRepository;
import com.example.books.repository.RatingRepository;
import com.example.books.service.RatingService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataGenerator implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;
    private final Faker faker = new Faker();
    private List<UUID> booksIds = new ArrayList<UUID>();

    @Override
    public void run(String... args) throws Exception {
        generateBooks(10);


    }
    private void generateBooks(int quantity){
        for(int i = 0; i < quantity; i++){
            Book book = Book.builder()

                    .title(faker.book().title())
                    .publishYear(faker.number().numberBetween(1960, Year.now().getValue()))
                    .author(faker.book().author())
                    .genre(faker.book().genre())
                    .build();
            bookRepository.save(book);
            generateRating(book,10);
        }
    }
    private void generateRating(Book book, int quantity){
        for(int i = 0; i < quantity; i++){
            ratingService.rateBook(book.getId(),faker.number().numberBetween(1,5));
        }
    }
}
