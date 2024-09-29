package com.example.books.repository;

import com.example.books.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Page<Book> findByTitleContainingIgnoreCase(PageRequest pageRequest, String contains);
}
