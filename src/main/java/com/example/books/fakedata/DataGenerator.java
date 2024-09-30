package com.example.books.fakedata;

import com.example.books.entity.Book;
import com.example.books.entity.Rating;
import com.example.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@RequiredArgsConstructor
public class DataGenerator implements CommandLineRunner {
    private final BookRepository bookRepository;

    private final Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {
        generateBooks(10);
        generateRating(20);
    }

    private void generateBooks(int quantity) {
        for (int i = 0; i < quantity; i++) {
            Book book = Book.builder()
                    .title(faker.book().title())
                    .publishYear(faker.number().numberBetween(1960, Year.now().getValue()))
                    .author(faker.book().author())
                    .genre(faker.book().genre())
                    .build();
            bookRepository.save(book);
        }
    }

    private void generateRating(int quantity) {
        for (int i = 0; i < quantity; i++) {
            Book book = bookRepository.findAll().get(faker.number().numberBetween(0,9));
            Rating rating = Rating.builder()
                    .book(book)
                    .rating(faker.number().numberBetween(1,5))
                    .build();
            book.addRating(rating);
            bookRepository.save(book);
        }
    }
}
