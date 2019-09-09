package pl.sda.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventorganizer.model.Event;

import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByOrderByStart();

    Event findEventByTitle(String title);

    Event findEventByEventId(Long id);

}
