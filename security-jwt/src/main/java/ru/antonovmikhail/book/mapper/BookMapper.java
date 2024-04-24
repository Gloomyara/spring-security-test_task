package ru.antonovmikhail.book.mapper;

import org.mapstruct.*;
import ru.antonovmikhail.book.model.Book;
import ru.antonovmikhail.book.model.dto.BookDtoIn;
import ru.antonovmikhail.book.model.dto.BookDtoOut;
import ru.antonovmikhail.book.model.dto.NewBookDto;

import java.util.List;

@Mapper
public interface BookMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book toEntity(NewBookDto dto);

    @Mapping(target = "authorName", source = "author.login")
    BookDtoOut toDto(Book entity);

    List<BookDtoOut> toDto(List<Book> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(BookDtoIn dto, @MappingTarget Book entity);
}
