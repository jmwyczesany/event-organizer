package pl.sda.eventorganizer.ControllerTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.FlashAttributeResultMatchers;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import pl.sda.eventorganizer.MockSecurityContext.WithMockCustomUser;
import pl.sda.eventorganizer.dto.EventForm;
import pl.sda.eventorganizer.exceptions.EventNotFoundException;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.service.EventService;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration

public class AddEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventServiceMock;

//    @MockBean
//    @Qualifier("eventValidator")
//    private Validator validator;


    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void getAddEventFormWithRoleOrganizerTest() throws Exception {
        mockMvc.perform(get("/addEvent").param("eventId", ""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("addEvent"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("addEventForm")).andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getAddEventFormWithRoleUserTest() throws Exception {
        mockMvc.perform(get("/addEvent").param("eventId", ""))
                .andExpect(status().isForbidden()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void getAddEventFormWithAnonymousUser() throws Exception {
        mockMvc.perform(get("/addEvent").param("eventId", ""))
                .andExpect(redirectedUrl("http://localhost/login")).andExpect(status().is3xxRedirection()).andDo(print());
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void getAddEventFormWihEventData() throws Exception {
        Event eventForUpdate = new Event();
        eventForUpdate.setEventId(11L);
        when(eventServiceMock.findEventById(11L)).thenReturn(eventForUpdate);
        mockMvc.perform(get("/addEvent").param("eventId", "11"))
                .andExpect(status().is2xxSuccessful()).andDo(print());
        assertThat(eventForUpdate).isEqualTo(eventServiceMock.findEventById(eventForUpdate.getEventId()));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void getAddEventWithInvalidEventId() throws Exception {
        when(eventServiceMock.findEventById(11L)).thenThrow(EventNotFoundException.class);
        mockMvc.perform(get("/addEvent").param("eventId", "11")).andExpect(status().isNotFound()).andDo(print());
        verify(eventServiceMock, times(1)).findEventById(11L);
        verifyNoMoreInteractions(eventServiceMock);
    }

//    I am aware of NPE in this test. And after a long research I have to admit that I have absolutely no idea how
//    to fix it. Probably, I missed some important information about mockMvc and now I can't figure it out. :(
//    so bye, my test. For now.
    @Test
//    this is my custom annotation, this user has ROLE_ORGANIZER
    @WithMockCustomUser
    public void postAddNewEvent() throws Exception {

        EventForm eventForm = new EventForm();
        eventForm.setTitle("TestTitle");
        eventForm.setDescription("012345678901234567890");
        eventForm.setStart(LocalDateTime.of(2022, 12, 12, 12, 12));
        eventForm.setEnd(LocalDateTime.of(2022, 12, 12, 14, 45));
        eventForm.setEventId(78L);

        Event recentlyAddedEvent = new Event();
        recentlyAddedEvent.setTitle(eventForm.getTitle());
        recentlyAddedEvent.setDescription(eventForm.getDescription());
        recentlyAddedEvent.setStart(eventForm.getStart());
        recentlyAddedEvent.setEnd(eventForm.getEnd());
        recentlyAddedEvent.setEventId(eventForm.getEventId());

        when(eventServiceMock.createNewEvent(anyString(), anyString(), any(LocalDateTime.class), any(LocalDateTime.class), any(User.class)))
                .thenReturn(recentlyAddedEvent);

        mockMvc.perform(post("/addEvent")
                .flashAttr("eventId", recentlyAddedEvent.getEventId())
                .param("eventId", " ")
                .param("title", "title")
                .param("description", "012345645678901234567890")
                .param("start", LocalDateTime.of(2020,10,1,12,12).toString())
                .param("end", LocalDateTime.of(2020,10,1,15,12).toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("eventId"))
                .andExpect(redirectedUrl("/eventCreated?eventId=" + recentlyAddedEvent.getEventId()));
//
//        mockMvc.perform(post("/addEvent")
//                .param("eventId", "78")
//                .param("title", "title")
//                .param("description", "012345645678901234567890")
//                .param("start", LocalDateTime.now().plusDays(2L).toString())
//                .param("end", LocalDateTime.now().plusDays(3L).toString()))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/eventCreated"));

}}
