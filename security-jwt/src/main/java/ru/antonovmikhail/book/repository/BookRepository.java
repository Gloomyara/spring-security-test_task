package ru.antonovmikhail.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.antonovmikhail.book.model.Book;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Override
    Page<Book> findAll(Pageable pageable);
}
