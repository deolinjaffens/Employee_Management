package com.util.exception;

/**
 *<p>
 *Exception class for Database related errors
 *<p>
 *@author Deolin Jaffens
 */

public class DatabaseException extends Exception {
    public DatabaseException(String errorMessage) {
        super(errorMessage);
    }
}