package pl.sda.eventorganizer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sda.eventorganizer.model.Comment;
import pl.sda.eventorganizer.model.Event;
import pl.sda.eventorganizer.repository.CommentRepository;
import pl.sda.eventorganizer.repository.EventRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class CommentService {

    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;

    }

    @Transactional
    public void createNewComment(String author, String text, Event event){
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setText(text);
        comment.setEvent(event);
        commentRepository.save(comment);
    }

    public Page<Comment> getAllComments(Long eventId, Pageable pageable){
        return commentRepository.findByEvent_EventId(eventId, pageable);
    }







}
