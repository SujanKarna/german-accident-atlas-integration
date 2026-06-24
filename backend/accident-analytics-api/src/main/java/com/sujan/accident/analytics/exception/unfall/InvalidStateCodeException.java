package com.sujan.accident.analytics.exception.unfall;

public class InvalidStateCodeException extends RuntimeException {
    public InvalidStateCodeException(String stateCode) {
        super("Invalid state code: " + stateCode + ". Expected a valid German state code (e.g., 01, 10, 15). StateCode ranges from 01 to 16. ");
    }
}
