package pl.sda.eventorganizer.dto;

import lombok.Data;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;


@Data
public class CommentForm {

    private Long id;
    private String text;
    private User commentAuthor;

}
