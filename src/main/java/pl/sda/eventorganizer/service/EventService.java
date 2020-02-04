package pl.sda.eventorganizer.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.repository.EventRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event createNewEvent(String title, String description, LocalDateTime startTime, LocalDateTime endTime, User author) {

        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setStart(startTime);
        event.setEnd(endTime);
        event.setAuthor(author);
        event.setAuthorName(author.getUserName());
        eventRepository.save(event);
        return event;
    }


    @Transactional
    public void updateTheEvent(String title, String description, LocalDateTime startTime, LocalDateTime endTime, User author, Long eventId) {
        Event updatedEvent = eventRepository.findEventByEventId(eventId);
        updatedEvent.setTitle(title);
        updatedEvent.setDescription(description);
        updatedEvent.setStart(startTime);
        updatedEvent.setEnd(endTime);
        updatedEvent.setAuthor(author);
        eventRepository.save(updatedEvent);
    }


    public Event findEventById(Long id) {
        return eventRepository.findEventByEventId(id);
    }

    public boolean checkUniqueTitleInDataBase(String title) {
        return eventRepository.findEventByTitle(title).isPresent();
    }

    public boolean checkIfUpdatedTitleRemainedSame(String title, Long eventId){
        return findEventById(eventId).getTitle().equals(title);
    }

    // memento reading docs, wyczesie
    public Page<Event> getAllEventsSortByWhatever(Pageable pageable) {
//        int pageSize = pageable.getPageSize();
//        int pageNumber = pageable.getPageNumber();
//        List<Event> sublistOfEvents;
//        List<Event> listOfAllEvents = eventRepository.findAll();
//        int firstItem = pageNumber * pageSize;

//        if (listOfAllEvents.size() < firstItem) {
//            sublistOfEvents = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(firstItem + pageSize, listOfAllEvents.size());
//            sublistOfEvents = listOfAllEvents.subList(firstItem, toIndex);
//        }
        //        return new PageImpl<>(sublistOfEvents, PageRequest.of(pageNumber, pageSize), listOfAllEvents.size());
        return eventRepository.findAll(pageable);
    }


    public List<Event> findAllEventsCreatedByUser(String userName) {
        return eventRepository.findAllByAuthorName(userName);
    }

    public void addEventToAuthorsList(Event event, String authorName) {
        findAllEventsCreatedByUser(authorName).add(event);
    }

    @Transactional
    public void deleteEventByEventId(Long eventId) {
        eventRepository.deleteEventByEventId(eventId);
    }


}
