package com.example.books.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "books")
@EqualsAndHashCode(exclude = {"ratings"})
@ToString(exclude = {"ratings"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String author;
    private String genre;
    private int publishYear;
    private double averageRating;

    @OneToMany(mappedBy = "book", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Rating> ratings = new ArrayList<>();

    @CreatedDate
    private ZonedDateTime createdAt;
    @LastModifiedDate
    private ZonedDateTime modifiedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = ZonedDateTime.now();
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setBook(this);
        changeAverageRating();
    }

    public void changeAverageRating() {
        if (ratings.isEmpty()) {
            this.averageRating = 0;
        } else {
            this.averageRating = ratings.stream()
                    .mapToDouble(Rating::getRating)
                    .average()
                    .orElse(0);
        }
    }
}
