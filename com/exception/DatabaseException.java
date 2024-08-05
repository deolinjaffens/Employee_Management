package com.exception;

/**
 *Exception class for Database related errors
 */

public class DatabaseException extends Exception {
    public DatabaseException(String errorMessage) {
        super(errorMessage);
    }
}