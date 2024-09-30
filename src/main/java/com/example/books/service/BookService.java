package com.example.books.service;

import com.example.books.dto.request.BookRequest;
import com.example.books.entity.Book;
import com.example.books.entity.Rating;
import com.example.books.repository.BookRepository;
import com.example.books.specification.BookFilter;
import com.example.books.specification.BookSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book createBook (BookRequest bookRequest){
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .genre(bookRequest.getGenre())
                .author(bookRequest.getAuthor())
                .publishYear(bookRequest.getPublishYear())
                .build();
        bookRepository.save(book);
        return book;
    }

    public void deleteBook (UUID id){
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Book was not found with ID: " + id);
        }
    }

    public Book updateBook (UUID id, BookRequest request){
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("book not found with id: " + id)
        );
        bookToUpdate.setTitle(request.getTitle());
        bookToUpdate.setAuthor(request.getAuthor());
        bookToUpdate.setGenre(request.getGenre());
        bookRepository.save(bookToUpdate);
        return bookToUpdate;
    }

    public Book getBookById(UUID id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("book not found with id: " + id));
    }

    public Page<Book> getFilteredBooks (BookFilter bookFilter, Pageable pageable) {
        BookSpecification spec = new BookSpecification(bookFilter);
            return bookRepository.findAll(spec, pageable);
    }
}
