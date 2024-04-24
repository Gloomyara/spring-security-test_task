package ru.antonovmikhail.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.antonovmikhail.user.model.Customer;
import ru.antonovmikhail.user.model.NewUserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    Customer findById(UUID id);

    List<Customer> findAll();

    Customer create(NewUserDto newUserDto);

    Customer update(NewUserDto newUserDto);

    Customer delete(UUID id);

}
