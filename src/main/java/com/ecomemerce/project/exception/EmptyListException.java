package com.ecomemerce.project.exception;

public class EmptyListException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmptyListException() {

    }
    public EmptyListException(String message) {
        super(message);
    }

}
