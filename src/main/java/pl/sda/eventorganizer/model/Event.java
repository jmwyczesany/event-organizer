package pl.sda.eventorganizer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private long eventId;

    @Column(nullable = false, unique = true)
    private String title;

    @Lob
    @Column(nullable = false)
    @Length(min = 20)
    private String description;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime start;
    @Column(nullable = false )
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime end;

    @ManyToMany(mappedBy = "userEvents")
    private List<User> users;


//    @OneToMany
//    private List<Comment> comments;


}
