package pl.sda.eventorganizer.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class EventForm {

    private long id;
    private String title;
    private String description;
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;




}
