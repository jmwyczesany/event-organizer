package pl.sda.eventorganizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no such event")
public class EventNotFoundException extends RuntimeException {


    public EventNotFoundException(String message) {
        super(message);
    }
}
