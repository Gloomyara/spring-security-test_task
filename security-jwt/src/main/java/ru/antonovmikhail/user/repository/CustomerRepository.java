package ru.antonovmikhail.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.antonovmikhail.user.model.Customer;
import ru.antonovmikhail.user.model.CustomerShort;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<CustomerShort> findByLogin(String login);
}
