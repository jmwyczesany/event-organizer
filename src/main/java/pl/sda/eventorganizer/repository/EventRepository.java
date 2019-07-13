package pl.sda.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventorganizer.model.Event;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
