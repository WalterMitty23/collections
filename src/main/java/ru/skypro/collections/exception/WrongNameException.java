package ru.skypro.collections.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongNameException extends RuntimeException {
       public WrongNameException(String message) {
        super(message);
    }
}
