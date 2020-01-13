package pl.sda.eventorganizer.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import pl.sda.eventorganizer.dto.RegisterForm;
import pl.sda.eventorganizer.model.Roles;
import pl.sda.eventorganizer.service.UserService;

public class AdminCreationConfiguration implements ApplicationListener<ContextRefreshedEvent> {


    public AdminCreationConfiguration(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (!userService.findByEmail("admin").isPresent()) {
            RegisterForm adminForm = new RegisterForm();
            adminForm.setEmail("admin");
            adminForm.setUserName("admin");
            adminForm.setPassword("admin");
            adminForm.setRole(Roles.ADMIN);


        }
    }
}
