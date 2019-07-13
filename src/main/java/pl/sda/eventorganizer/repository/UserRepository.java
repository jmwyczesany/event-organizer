package pl.sda.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.eventorganizer.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUserName(String userName);
    User findByEmail(String email);
}
