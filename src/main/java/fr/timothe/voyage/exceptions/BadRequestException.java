package fr.timothe.voyage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(List<String> badRequestMessages) {
        super( String.join(", ", badRequestMessages));
    }
}
