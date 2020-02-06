package pl.sda.eventorganizer.service;

import io.codearte.jfairy.Fairy;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.repository.EventRepository;
import pl.sda.eventorganizer.model.Roles;
import pl.sda.eventorganizer.repository.UserRepository;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// some random data for H2 database
@Service
@Profile("dev")
public class DbInit implements ApplicationRunner {

    private EventRepository eventRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;


    public DbInit(EventRepository eventRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

// finding random Datetime in the future mm nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn nn b b b  n   n cxds <-- sorry, that's my cat (his name is Richard)
    private long getRandomDateLong(){
        LocalDateTime begin = LocalDateTime.now().plusDays(2L);
        LocalDateTime end = begin.plusMonths(12L);
        ZonedDateTime zdt = begin.atZone(ZoneId.systemDefault());
        ZonedDateTime zdt2 = end.atZone(ZoneId.systemDefault());
        long beginTime = zdt.toInstant().toEpochMilli();
        long endTime = zdt2.toInstant().toEpochMilli();
        long diff = endTime - beginTime + 1;
        return beginTime + (long) (Math.random() * diff);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User admin = new User("admin@admin.com", "admin",passwordEncoder.encode("admin123"));
        User userTheCreator = new User ("organizer@organizer.com", "organizer", passwordEncoder.encode("organizer123"));
        userTheCreator.setRole(Roles.ORGANIZER);
        admin.setRole(Roles.ADMIN);
        User user = new User("user@user.com", "user", passwordEncoder.encode("user123"));

        List<User> users = Arrays.asList(admin, user, userTheCreator);
        userRepository.saveAll(users);
        LocalDateTime randomTime;
        Fairy fairy = Fairy.create();
        for (int i = 1; i <= 20; i++){
            randomTime = Instant.ofEpochMilli(getRandomDateLong()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            Event someEvent = new Event(fairy.textProducer().randomString(new Random().nextInt(50)),
                    fairy.textProducer().paragraph(i+2), randomTime, randomTime.plusHours(2L), userTheCreator, userTheCreator.getUserName());
           eventRepository.save(someEvent);
        }
    }
}