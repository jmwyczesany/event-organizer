package pl.sda.eventorganizer.dto;

import lombok.Data;
import pl.sda.eventorganizer.model.Event;


@Data
public class CommentForm {

    private long id;

    private String text;

    private String author;


}
