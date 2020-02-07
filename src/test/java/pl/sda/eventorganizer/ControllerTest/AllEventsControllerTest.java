package pl.sda.eventorganizer.ControllerTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.service.EventService;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AllEventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EventService eventServiceMock;


    @Test
    public void getAllEventsAsPageTest() throws Exception {

        List<Event> eventList = new ArrayList<>();
        Page<Event> eventPage = new PageImpl<>(eventList);
        when(eventServiceMock.getAllFutureEvents(any(Pageable.class))).thenReturn(eventPage);

        mockMvc.perform(get("/allEvents/page/{pageNo}", 1 ))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("allEvents"))
                .andExpect(model().attributeExists("allEvents"))
                .andExpect(model().attributeExists("pagination"))
                .andExpect(model().size(2));
    }

}
