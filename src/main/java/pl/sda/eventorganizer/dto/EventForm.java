package pl.sda.eventorganizer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sda.eventorganizer.validation.UniqueTitle;

import java.time.LocalDateTime;

@Data
//default constructor for model
@NoArgsConstructor
public class EventForm {

    private Long eventId;
    private String title;
    private String description;
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;


//    constructor with arguments for get request
    public EventForm(long eventId, String title, String description, LocalDateTime start, LocalDateTime end) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;

    }



}
