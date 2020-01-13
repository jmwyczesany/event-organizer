package pl.sda.eventorganizer.MockSecurityContext;

import org.springframework.security.test.context.support.WithSecurityContext;
import pl.sda.eventorganizer.model.Roles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithMockCustomUser {

    String userName() default "TestOrganizer";

    String email() default "test@test.com";

    Roles role() default Roles.ORGANIZER;



}
