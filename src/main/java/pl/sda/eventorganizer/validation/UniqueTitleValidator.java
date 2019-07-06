package pl.sda.eventorganizer.validation;

import pl.sda.eventorganizer.repository.EventRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//Q: why I don't need any @Component annotation or any stuff like this?

// ANSW: Spring framework automatically detects all classes which implements ConstraintValidator interface -> instantiates them &
// wires dependencies like in a common Spring bean

public class UniqueTitleValidator implements ConstraintValidator<UniqueTitle, String> {


   private EventRepository eventRepository;

   public UniqueTitleValidator(EventRepository eventRepository) {
      this.eventRepository = eventRepository;
   }

   public void initialize(UniqueTitle constraint) {
   }

   public boolean isValid(String title, ConstraintValidatorContext context) {

      return title != null && !eventRepository.findEventByTitle(title).isPresent();
   }
}
