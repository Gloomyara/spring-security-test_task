package ru.antonovmikhail.book.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.antonovmikhail.book.model.Book;
import ru.antonovmikhail.user.model.Customer;
import ru.antonovmikhail.util.Pager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BookRepository repository;
    private final Customer customer = Customer.builder()
            .login("test1")
            .email("test1@test.omg")
            .build();


    @BeforeEach
    void setUp() {
        entityManager.persist(customer);
    }

    @Test
    void findAll() {
        for (int i = 1; i <= 15; i++) {
            entityManager.persist(new Book(
                    null,
                    customer,
                    "wtvr" + i,
                    "wtvr" + i,
                    LocalDateTime.now().minusMinutes(i)));
        }
        Page<Book> foundBooks = repository
                .findAll(Pageable.ofSize(10));
        assertThat(foundBooks.toList()).hasSize(10);
        foundBooks = repository
                .findAll(new Pager(10, 10, Sort.by("publishDate")));
        assertThat(foundBooks.toList()).hasSize(5);
    }
}