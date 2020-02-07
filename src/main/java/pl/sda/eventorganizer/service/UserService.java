package pl.sda.eventorganizer.service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.eventorganizer.UserPrincipal;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.repository.EventRepository;
import pl.sda.eventorganizer.model.Roles;
import pl.sda.eventorganizer.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, EventRepository eventRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user==null)
            throw new UsernameNotFoundException("User " + email + " doesn't exist");

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        return new UserPrincipal(user);

    }


    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public Optional<User> findByUserName(String username) {
        return Optional.ofNullable(userRepository.findByUserName(username));
    }


    @Transactional
    public void save(String email, String userName, String password, String confirmPassword, Roles role) {

        final User myUser = new User();
        myUser.setEmail(email);
        myUser.setUserName(userName);
        myUser.setPassword(passwordEncoder.encode(password));
        myUser.setConfirmPassword(confirmPassword);
        myUser.setActive(1);
        myUser.setRole(role);
        userRepository.save(myUser);

        if(userRepository.findByUserName(userName).getId() == 1L) {
            myUser.setRole(Roles.ADMIN);
            userRepository.save(myUser);
        }

    }

    public void save(User user){
        userRepository.save(user);

    }


    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public List<Event> findAllUsersEvents(User user){
        return eventRepository.findEventsByUsers(user);
    }

    public void addEventToUsersList(Event event, User user) {
        eventRepository.findEventsByUsers(user).add(event);
    }


}
