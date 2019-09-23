package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.repository.CommentRepository;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    ImageService imageService;

    //This controller is called when the request type is of 'image/{imageId}/{imageTitle}/comments
    //This returns and redirects to images/imageId/imageTitle
    //This controller is used to add a comment to an image
    @RequestMapping(value = "image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@PathVariable("imageId") Integer imageId,
                               @PathVariable("imageTitle") String imageTitle,
                               @RequestParam String comment,
                               Model model,
                               HttpSession session) {
        Image image = imageService.getImage(imageId);
        User user = (User) session.getAttribute("loggeduser");
        Comment comment1 = commentService.saveComments(comment, image, user);
        return "redirect:/images/"+imageId+"/"+imageTitle;
    }
}
