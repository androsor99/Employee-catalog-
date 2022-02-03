package com.epam.rd.autotasks.springemployeecatalog.service;

import java.util.Arrays;
import java.util.Optional;

public enum Sorter {

    LASTNAME("lastName"),
    HIRED("hireDate"),
    POSITION("position"),
    SALARY("salary");

    private final String sortField;

    Sorter(String sort) {
        this.sortField = sort;
    }

    public static Optional<String> find(String sort) {
        return Arrays.stream(values())
                .filter(item -> item.name().equalsIgnoreCase(sort))
                .map(Sorter::getSortField)
                .findFirst();
    }

    public String getSortField() {
        return sortField;
    }
}
