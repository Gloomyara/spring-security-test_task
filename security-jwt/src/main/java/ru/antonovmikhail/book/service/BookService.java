package ru.antonovmikhail.book.service;

import org.springframework.data.domain.Pageable;
import ru.antonovmikhail.book.model.dto.BookDtoIn;
import ru.antonovmikhail.book.model.dto.BookDtoOut;
import ru.antonovmikhail.book.model.dto.NewBookDto;

import java.util.List;
import java.util.UUID;

public interface BookService {


    BookDtoOut getBookById(UUID id);

    BookDtoOut saveNewBook(NewBookDto dto);

    BookDtoOut update(BookDtoIn dtoIn);

    List<BookDtoOut> findAll(Pageable pageable);

    BookDtoOut delete(UUID id);
}
