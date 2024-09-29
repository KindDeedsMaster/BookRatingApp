package com.example.books.specification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFilter {
    private String title;
    private String author;
    private Integer minYear;
    private Integer maxYear;
    private Double minRating;
    private Double maxRating;

}
