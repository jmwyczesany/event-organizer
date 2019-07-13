package pl.sda.eventorganizer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participation {

    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime date;

    private boolean interested;

    private boolean confirmed;


}
