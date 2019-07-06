package pl.sda.eventorganizer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Lob
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToMany
    private List<User> users;
//    private List<Comment> comments;

}
