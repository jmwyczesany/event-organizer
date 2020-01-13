package pl.sda.eventorganizer.ServiceTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.sda.eventorganizer.dto.EventForm;
import pl.sda.eventorganizer.service.EventValidator;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class EventValidatorTest {

    @Test
    public void validateNoErrorsTest(){
        EventForm eventForm = new EventForm();
        eventForm.setTitle("TestTitle");
        eventForm.setDescription("01234567890123456789");
        eventForm.setStart(LocalDateTime.of(2022, 12,12, 12, 12));
        eventForm.setEnd(LocalDateTime.of(2022,12,12,14,45));
        eventForm.setEventId(78L);
        EventValidator eventValidator = new EventValidator();
        Errors errors = new BeanPropertyBindingResult(eventForm, "eventForm");
        eventValidator.validate(eventForm, errors);
        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    public void validateHasErrorsTest(){
        EventForm eventForm = new EventForm();
        eventForm.setTitle("");
        eventForm.setDescription("0123456");
        eventForm.setStart(LocalDateTime.of(2022, 12,12, 12, 12));
        eventForm.setEnd(LocalDateTime.of(2020,12,12,14,45));
        eventForm.setEventId(78L);
        EventValidator eventValidator = new EventValidator();
        Errors errors = new BeanPropertyBindingResult(eventForm, "eventForm");
        eventValidator.validate(eventForm, errors);
        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(3);
        assertThat(Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage()).isEqualTo("All fields are required");
        assertThat(Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage()).isEqualTo("Description must be at least 20 characters long");
        assertThat(Objects.requireNonNull(errors.getFieldError("start")).getCode()).isEqualTo("invalid.time.range");

    }


}
