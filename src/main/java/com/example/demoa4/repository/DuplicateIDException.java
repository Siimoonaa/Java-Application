package com.example.demoa4.repository;

public class DuplicateIDException extends RepositoryException {
    public DuplicateIDException(String message) {
        super(message);
    }
}
