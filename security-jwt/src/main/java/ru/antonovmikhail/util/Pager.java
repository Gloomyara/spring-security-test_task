package ru.antonovmikhail.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Pager extends PageRequest {
    
    public Pager(int from, int size, Sort sort) {
        super(from / size, size, sort);
    }
}
