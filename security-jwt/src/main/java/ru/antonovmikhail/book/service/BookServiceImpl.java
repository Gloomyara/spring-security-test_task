package ru.antonovmikhail.book.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antonovmikhail.book.mapper.BookMapper;
import ru.antonovmikhail.book.model.Book;
import ru.antonovmikhail.book.model.dto.BookDtoIn;
import ru.antonovmikhail.book.model.dto.BookDtoOut;
import ru.antonovmikhail.book.model.dto.NewBookDto;
import ru.antonovmikhail.book.repository.BookRepository;
import ru.antonovmikhail.user.model.Customer;
import ru.antonovmikhail.user.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final CustomerRepository customerRepository;
    private final BookMapper mapper = Mappers.getMapper(BookMapper.class);

    @Transactional(readOnly = true)
    @Override
    public BookDtoOut getBookById(UUID id) throws EntityNotFoundException {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public BookDtoOut saveNewBook(NewBookDto dtoIn) throws EntityNotFoundException {
        Customer customer = customerRepository.getReferenceById(dtoIn.getAuthorId());
        Book book = mapper.toEntity(dtoIn);
        book.setAuthor(customer);
        repository.save(book);
        return mapper.toDto(repository.save(book));
    }


    @Override
    public BookDtoOut update(BookDtoIn dtoIn) throws EntityNotFoundException {
        if (customerRepository.existsById(dtoIn.getAuthorId())) {
            Book book = repository.findById(dtoIn.getId()).orElseThrow(() -> new EntityNotFoundException());
            mapper.update(dtoIn, book);
            return mapper.toDto(repository.save(book));
        }
        throw new EntityNotFoundException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDtoOut> findAll(Pageable pageable) {
        return mapper.toDto(repository.findAll((org.springframework.data.domain.Pageable) pageable).toList());
    }

    @Override
    public BookDtoOut delete(UUID id) throws EntityNotFoundException {
        Book book = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        repository.deleteById(id);
        return mapper.toDto(book);
    }
}
