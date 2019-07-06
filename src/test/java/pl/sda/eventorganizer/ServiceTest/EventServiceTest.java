package pl.sda.eventorganizer.ServiceTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import pl.sda.eventorganizer.controller.AllEventsController;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.repository.EventRepository;
import pl.sda.eventorganizer.service.EventService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {


    @Mock
    private EventRepository eventRepositoryMock;

    @InjectMocks
    private EventService eventService;

    @Test
    public void getAllEventsTest() {
        List<Event> eventList = new ArrayList<>();
        Page <Event> eventPage = new PageImpl<>(eventList);
        when(eventRepositoryMock.findAll(any(Pageable.class))).thenReturn(eventPage);
        Page<Event> allEventsSortByWhatever = eventService.getAllEventsSortByWhatever(Pageable.unpaged());
        assertThat(eventPage).isEqualTo(allEventsSortByWhatever);
        Assert.assertEquals(eventPage, allEventsSortByWhatever);
        verify(eventRepositoryMock, times(1)).findAll(Pageable.unpaged());
    }

    @Test
    public void createNewEventTest() {

        User user = new User();
        user.setUserName("TestUser");
        Event expectedEvent = new Event("Bla", "blablabla", LocalDateTime.of(2020,1,1, 12, 0),
                LocalDateTime.of(2020,1,2, 12, 0), user, user.getUserName());

        Event newEvent = eventService.
                createNewEvent(expectedEvent.getTitle(), expectedEvent.getDescription(), expectedEvent.getStart(), expectedEvent.getEnd(), expectedEvent.getAuthor());
        assertThat(newEvent).isNotNull();
        assertThat(newEvent).isEqualToComparingFieldByField(expectedEvent);
        verify(eventRepositoryMock, times(1)).save(newEvent);

    }

    @Test
    public void updatingEventsTest() {
        User user = new User();
        user.setUserName("TestUser");
        Long eventId = 55L;
        Event testEvent = new Event("Bla", "blablabla", LocalDateTime.now().plusDays(2L),
                LocalDateTime.now().plusDays(4L), user, user.getUserName());

        Event expectedEvent = new Event("Bla1", "blablabla1", LocalDateTime.now().plusDays(2L),
                LocalDateTime.now().plusDays(4L), user, user.getUserName());

        when(eventRepositoryMock.findEventByEventId(eventId)).thenReturn(testEvent);

        eventService.updateTheEvent(expectedEvent.getTitle(), expectedEvent.getDescription(),
                expectedEvent.getStart(), expectedEvent.getEnd(), expectedEvent.getAuthor(), eventId);
        assertThat(testEvent).isEqualToComparingFieldByField(expectedEvent);
        verify(eventRepositoryMock, times(1)).save(testEvent);

    }



}
