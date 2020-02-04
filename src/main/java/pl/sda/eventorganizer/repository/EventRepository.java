package pl.sda.eventorganizer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;

import java.util.List;
import java.util.Optional;


//JPARepository extends PagingAndSortingRepository, cool

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {


    Page<Event> findAll(Pageable pageable);

    List<Event> findAllByAuthorName(String author);

    Optional<Event> findEventByTitle(String title);

//    Event findEventByTitle(String title);

    Event findEventByEventId(Long id);

    void deleteEventByEventId(Long id);

    List<Event> findEventsByUsers(User user);



}
