package pl.sda.eventorganizer.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sda.eventorganizer.validation.UniqueTitle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Event extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id", nullable = false)
    private long eventId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime start;
    @Column(nullable = false )
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime end;

    @ManyToMany(mappedBy = "userEvents")
    @JsonIgnore
    private List<User> users;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User author;

    private String authorName;

//    public Event() {
//    }

    public Event(String title, @Length(min = 20) String description, LocalDateTime start, LocalDateTime end, User author, String authorName) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.author = author;
        this.authorName = author.getUserName();
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", authorName='" + authorName + '\'' +
                '}';
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    //    @OneToMany
//    private List<Comment> comments;


}
