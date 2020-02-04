package pl.sda.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUserName(String userName);
    User findByEmail(String email);
    Optional<User> findById(Long id);



}
