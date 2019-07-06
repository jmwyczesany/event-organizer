package pl.sda.eventorganizer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;

import javax.validation.constraints.NotNull;

@Data
//default constructor for modeling
@NoArgsConstructor
public class AddUserToAnEventForm {

    @NotNull
    private Long eventId;

    @NotNull
    private Long userId;

    private User user;

    private Event event;

// constr. with arguments for get Request
    public AddUserToAnEventForm(User user, Event event) {
        this.user = user;
        this.event = event;
    }
}
