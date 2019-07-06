package pl.sda.eventorganizer.ServiceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.eventorganizer.model.Roles;
import pl.sda.eventorganizer.model.User;
import pl.sda.eventorganizer.repository.EventRepository;
import pl.sda.eventorganizer.repository.UserRepository;
import pl.sda.eventorganizer.service.UserService;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
   private UserRepository userRepositoryMock;

    @Mock
    private EventRepository eventRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private UserService userService;

    @Test
    public void saveNewUserTest(){

        User expectedUser = new User("expected@expected.com", "expectedName", passwordEncoderMock.encode("abc"));
        expectedUser.setRole(Roles.ORGANIZER);
        System.out.println(expectedUser);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        userService.save(expectedUser.getEmail(), expectedUser.getUserName(), expectedUser.getPassword(),
                        expectedUser.getConfirmPassword(), expectedUser.getRole());
        verify(userRepositoryMock).save(userCaptor.capture());
        assertThat(expectedUser).isEqualTo(userCaptor.getValue());
        assertThat(expectedUser).isEqualToComparingFieldByField(userCaptor.getValue());
    }


}
