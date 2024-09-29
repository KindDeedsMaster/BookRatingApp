package com.example.books.specification;

import com.example.books.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BookSpecification implements Specification<Book> {
    private final BookFilter bookFilter;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (bookFilter.getTitle() != null && !bookFilter.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + bookFilter.getTitle() + "%"));
        }

        if (bookFilter.getAuthor() != null && !bookFilter.getAuthor().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("author"), "%" + bookFilter.getAuthor() + "%"));
        }

        if (bookFilter.getMinYear() != null && bookFilter.getMaxYear() != null) {
            predicates.add(criteriaBuilder.between(root.get("publishYear"), bookFilter.getMinYear(), bookFilter.getMaxYear()));
        } else if (bookFilter.getMinYear() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishYear"), bookFilter.getMinYear()));
        } else if (bookFilter.getMaxYear() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("publishYear"), bookFilter.getMaxYear()));
        }





        if (bookFilter.getMinRating() != null && bookFilter.getMaxRating() != null) {
            predicates.add(criteriaBuilder.between(root.get("rating"), bookFilter.getMinRating(), bookFilter.getMaxRating()));
        } else if (bookFilter.getMinRating() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), bookFilter.getMinRating()));
        } else if (bookFilter.getMaxRating() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), bookFilter.getMaxRating()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
