package pl.sda.eventorganizer.model;


import lombok.Data;
import javax.persistence.*;
import java.util.*;


@Data
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Transient
    private String confirmPassword;

    @Column(nullable = false)
    private int active;

    @Column
    @Enumerated(EnumType.STRING)
    private Roles role;

    private String permission = "";

    @ManyToMany
    @JoinTable(name = "user_events", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> userEvents;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Event> eventsCreatedByUser;

    @OneToMany(mappedBy = "commentAuthor", cascade = CascadeType.ALL)
    private List<Comment> userComment;


//    default: role USER with status: active. Maybe default role is not a good idea, idk, maybe i will change it later
//    or private Status ACTIVE, BLOCKED? (enum?)
    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.role = Roles.USER;
        this.active = 1;
    }

    public User(){}

    public List<String> getPermissionsAsList(){
        if(permission.length() > 0) {
            return Arrays.asList(this.permission.split(","));
        } return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                ", permission='" + permission + '\'' +
                '}';
    }

    public static final class UserBuilder {
        private long id;
        private String email;
        private String userName;
        private String password;
        private String confirmPassword;
        private int active;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public UserBuilder withActive(int active) {
            this.active = active;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setUserName(userName);
            user.setPassword(password);
            user.setConfirmPassword(confirmPassword);
            user.setActive(active);
            return user;
        }
    }
}
