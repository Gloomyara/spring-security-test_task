package ru.antonovmikhail.book.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.antonovmikhail.book.model.dto.BookDtoIn;
import ru.antonovmikhail.book.model.dto.BookDtoOut;
import ru.antonovmikhail.book.model.dto.NewBookDto;
import ru.antonovmikhail.book.service.BookService;
import ru.antonovmikhail.util.Pager;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@Controller
@RequestMapping("api/v1")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    public ResponseEntity<List<BookDtoOut>> getAll(
            @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC)
            Pager pageable) {
        log.info("Received GET api/v1/books request.");
        return ResponseEntity.ok(bookService.findAll(pageable));
    }


    @GetMapping("books/{id}")
    @PreAuthorize("#user == authentication.principal.username")
    public ResponseEntity<BookDtoOut> getBook(
            @PathVariable UUID id) {
        log.info("Received GET api/v1/books/{} request.", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("book")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<BookDtoOut> saveNewBook(
            @Valid @RequestBody NewBookDto dtoIn) {
        log.info("Received POST api/v1/books request, orderDtoIn: {}.", dtoIn);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.saveNewBook(dtoIn));
    }

    @PutMapping("book")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<BookDtoOut> put(@Valid @RequestBody BookDtoIn dtoIn) {
        log.info("Received PUT api/v1/books request, orderDtoIn = {}", dtoIn);
        return ResponseEntity.ok(bookService.update(dtoIn));
    }

    @DeleteMapping("books/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<BookDtoOut> delete(@org.hibernate.validator.constraints.UUID UUID id) {
        log.info("Received DELETE api/v1/books/{} request.", id);
        return ResponseEntity.ok(bookService.delete(id));
    }
}



