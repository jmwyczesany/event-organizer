package pl.sda.eventorganizer.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Lob
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User commentAuthor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Event event;
}
