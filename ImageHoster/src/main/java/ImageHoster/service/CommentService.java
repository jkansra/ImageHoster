package ImageHoster.service;


import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    //This method calls getComments method from the repository
    //It gets comments for a particular image
    public List<Comment> getCommentsForImage(Image image, User user) {
        return commentRepository.getComments(image, user);
    }

    //This method calls saveComment method from the repository
    //It saves the comments done by a user to an image
    public Comment saveComments(String text, Image image, User user) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setImage(image);
        comment.setText(text);
        comment.setCreatedDate(LocalDate.now());
        commentRepository.saveComment(comment);
        return comment;
    }

}
