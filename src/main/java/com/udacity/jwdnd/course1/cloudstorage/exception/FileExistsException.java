package com.udacity.jwdnd.course1.cloudstorage.exception;

public class FileExistsException extends RuntimeException {

    public FileExistsException(String message) {
        super(message);
    }
}
