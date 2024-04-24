package ru.antonovmikhail.user.model;

import java.util.Set;

public interface CustomerShort {

    String getLogin();

    void setLogin(String login);

    String getPassword();

    void setPassword(String password);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);
}
