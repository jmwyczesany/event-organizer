package pl.sda.eventorganizer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventorganizer.model.Comment;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByEvent_EventId(Long eventId, Pageable pageable);
    Optional<Comment> findByCommentIdAndEvent_EventId(Long commentId, Long eventID);

}
