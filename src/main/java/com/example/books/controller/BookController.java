package com.example.books.controller;

import com.example.books.dto.request.BookRequest;
import com.example.books.entity.Book;
import com.example.books.service.BookService;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBook(@PathVariable @Nonnull UUID id) {
        Book book= bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Book>> findAllBooks(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                   @RequestParam(defaultValue = "2") @Min(0) Integer listSize,
                                                   @RequestParam(defaultValue = "title") String sortBy,
                                                   @RequestParam(defaultValue = "false") boolean sortDesc,
                                                   @RequestParam(required = false) String contains) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(page, listSize, sort);
        return ResponseEntity.ok().body(bookService.getAllBooks(pageRequest, contains));
    }

    @PostMapping
    public ResponseEntity<Book> createBook (@RequestBody BookRequest request){
        Book book = bookService.createBook(request);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Book> updateBook (@RequestBody BookRequest request,
                                            @PathVariable @Nonnull UUID id){
        Book book = bookService.updateBook(id, request);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable @Nonnull UUID id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
