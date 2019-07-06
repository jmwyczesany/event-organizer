package pl.sda.eventorganizer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;

    private Event event;

    private String userName;

    private String password;

    private String firstName;
    private String lastName;
    @ManyToMany(mappedBy = "")
    private List<Event> userEvents;

}
