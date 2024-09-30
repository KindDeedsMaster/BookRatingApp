package com.example.books.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Rating {

    public Rating(Book book, int rating) {
        this.book = book;
        this.rating = rating;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Min(value = 1)
    @Max(value = 5)
    private int rating;
}
