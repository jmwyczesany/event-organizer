package pl.sda.eventorganizer.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
// here's a place where I can add multiple Validators if I, one day, will come to idea that I want this annotation works on some different types
// ...You never know
@Constraint(validatedBy = UniqueTitleValidator.class)
public @interface UniqueTitle {

    String message() default "Given title is already defined in our database. Please choose another name for your event";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
