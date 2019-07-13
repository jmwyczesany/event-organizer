package pl.sda.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventorganizer.model.Participation;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    
}
