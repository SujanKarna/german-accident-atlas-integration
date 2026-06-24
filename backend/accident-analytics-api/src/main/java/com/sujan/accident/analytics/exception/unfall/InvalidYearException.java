package com.sujan.accident.analytics.exception.unfall;

public class InvalidYearException extends RuntimeException {
    public InvalidYearException(int year) {

        super("Invalid year: " + year);
    }
}
