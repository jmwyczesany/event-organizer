package pl.sda.eventorganizer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


//JPARepository extends PagingAndSortingRepository, cool

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {


    Page<Event> findAll(Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.start >= :now")
    Page<Event> findAllFutureEvents(Pageable pageable, @Param("now") LocalDateTime now);

    List<Event> findAllByAuthorName(String author);

    Optional<Event> findEventByTitle(String title);

//    for now - if start is in the past, then event is in Archive, maybe I will change it later, idk;
    @Query("SELECT e FROM Event e WHERE e.start < :now")
    Page<Event> findAllEventsInArchive(Pageable pageable, @Param("now")LocalDateTime now);

//    Event findEventByTitle(String title);

    Event findEventByEventId(Long id);

    void deleteEventByEventId(Long id);

    List<Event> findEventsByUsers(User user);

    /*next four methods are used in searching*/

    @Query("SELECT e FROM Event e WHERE e.title LIKE %:titlePhrase% AND e.start < :today AND e.end > :today")
    Page<Event> findAllOngoingEventsByTitlePhrase(Pageable pageable, @Param("titlePhrase") String titlePhrase, @Param("today") LocalDateTime today);

    @Query("SELECT e FROM Event e WHERE e.title LIKE %:titlePhrase% AND e.end < :today")
    Page<Event> findAllPastEventsByTitlePhrase(Pageable pageable, @Param("titlePhrase") String titlePhrase, @Param("today") LocalDateTime today);

    @Query("SELECT e FROM Event e WHERE e.title LIKE %:titlePhrase% AND e.start > :today")
    Page<Event> findAllFutureEventsByTitlePhrase(Pageable pageable, @Param("titlePhrase") String titlePhrase, @Param("today") LocalDateTime today);

    @Query("SELECT e FROM Event e WHERE e.title LIKE %:titlePhrase%")
    Page<Event> findAllEventsByTitlePhrase(Pageable pageable, @Param("titlePhrase") String titlePhrase);

}
