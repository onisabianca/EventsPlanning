package com.project.internship.exception;

public class DuplicateKeyConstraintException extends RuntimeException {
    public DuplicateKeyConstraintException(String s) {
        super(s);
    }
}
