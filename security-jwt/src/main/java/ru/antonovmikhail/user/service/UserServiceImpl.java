package ru.antonovmikhail.user.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antonovmikhail.user.model.Customer;
import ru.antonovmikhail.user.model.CustomerShort;
import ru.antonovmikhail.user.model.NewUserDto;
import ru.antonovmikhail.user.repository.CustomerRepository;
import ru.antonovmikhail.util.config.authentication.CustomUserDetails;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@Qualifier("CustomerService")
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerShort customerShort = repository.findByLogin(username)
                .orElseThrow(()-> new UsernameNotFoundException("Unknown user: "+ username));
        return new CustomUserDetails(customerShort);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Customer create(NewUserDto dtoIn) {
        Customer customer = Customer.builder()
                .login(dtoIn.getLogin())
                .email(dtoIn.getEmail())
                .build();
        return repository.save(customer);
    }

    @Override
    public Customer update(NewUserDto dtoIn) {
        Customer customer = repository.findById(dtoIn.getId()).orElseThrow(() -> new EntityNotFoundException());
        customer.setLogin(dtoIn.getLogin());
        customer.setEmail(dtoIn.getEmail());
        return repository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Customer delete(UUID id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        repository.deleteById(id);
        return customer;
    }
}
