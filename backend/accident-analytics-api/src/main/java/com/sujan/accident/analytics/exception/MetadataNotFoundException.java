package com.sujan.accident.analytics.exception;

public class MetadataNotFoundException extends RuntimeException {
    public MetadataNotFoundException(String message) {
        super("No metadata found for dataset: " + message);
    }
}
