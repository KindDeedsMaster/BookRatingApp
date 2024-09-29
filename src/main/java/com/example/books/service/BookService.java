package com.example.books.service;

import com.example.books.dto.request.BookRequest;
import com.example.books.entity.Book;
import com.example.books.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    final private BookRepository bookRepository;

    public Book createBook (BookRequest bookRequest){
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
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
        bookToUpdate.setDescription(request.getDescription());
        bookRepository.save(bookToUpdate);
        return bookToUpdate;
    }

    public Book getBookById(UUID id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("book not found with id: " + id));
    }

    public Page<Book> getAllBooks (PageRequest pageRequest, String contains) {
        if (contains == null){
            return bookRepository.findAll(pageRequest);
        } else{
            return bookRepository.findByTitleContainingIgnoreCase(pageRequest, contains);
        }

    }


}
