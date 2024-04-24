package ru.antonovmikhail.user.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.antonovmikhail.user.model.Customer;
import ru.antonovmikhail.user.model.NewUserDto;
import ru.antonovmikhail.user.service.UserService;
import ru.antonovmikhail.util.Views;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Slf4j
public class UserController {

    private final UserService userService;

    @JsonView(Views.UserSummary.class)
    @GetMapping("users")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<Customer>> getAll() {
        log.info("Received GET api/v1/users request.");
        return ResponseEntity.ok(userService.findAll());
    }

    @JsonView(Views.UserDetails.class)
    @GetMapping("user/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Customer> getById(@org.hibernate.validator.constraints.UUID UUID id) {
        log.info("Received GET api/v1/user/{} request.", id);
        return ResponseEntity.ok(userService.findById(id));
    }

    @JsonView(Views.UserDetails.class)
    @PostMapping("user")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Customer> post(@Valid @RequestBody NewUserDto dtoIn) {
        log.info("Received POST api/v1/user request, userDtoIn = {}", dtoIn);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(dtoIn));
    }

    @JsonView(Views.UserDetails.class)
    @PutMapping("user")
    @PreAuthorize("#user == authentication.principal.username")
    public ResponseEntity<Customer> put(@Valid @RequestBody NewUserDto dtoIn) {
        log.info("Received PUT api/v1/user request, userDtoIn = {}", dtoIn);
        return ResponseEntity.ok(userService.update(dtoIn));
    }

    @JsonView(Views.UserDetails.class)
    @DeleteMapping("user/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Customer> delete(@org.hibernate.validator.constraints.UUID UUID id) {
        log.info("Received DELETE api/v1/user/{} request.", id);
        return ResponseEntity.ok(userService.delete(id));
    }
}
