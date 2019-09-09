package pl.sda.eventorganizer.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.repository.EventRepository;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class EventService {

    private final EventRepository eventRepository;


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Page<Event> findAllEvents(Pageable pageable){
       return eventRepository.findAll(pageable);
    }



    @Transactional
    public Event createNewEvent(String title, String description, LocalDateTime startTime, LocalDateTime endTime){

        final Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setStart(startTime);
        event.setEnd(endTime);
        eventRepository.save(event);

        return event;
    }


    public Event findEventByTitle(String title){
        return eventRepository.findEventByTitle(title);
    }

    public Event findEventById(Long id) {
        return eventRepository.findEventByEventId(id);
    }




}
