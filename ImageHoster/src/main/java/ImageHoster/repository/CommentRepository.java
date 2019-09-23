package ImageHoster.repository;


import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class CommentRepository {
    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the Comment object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    //This method saves the comment object into the database
    public boolean saveComment(Comment comment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        boolean isSuccess = false;
        try {
            transaction.begin();
            em.persist(comment);
            transaction.commit();
            isSuccess = true;
        } catch (Exception e) {
            transaction.rollback();
            isSuccess = false;
        }
        return isSuccess;
    }

    //This method takes Image and User as arguments
    //This method fetches all the comments done by the user for an image
    //Incase there are no results from the query, null is returned
    public List<Comment> getComments(Image image, User user) {
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Comment> typedQuery = em.createQuery("SELECT c FROM Comment c WHERE c.image = :image and c.user = :user", Comment.class);
            typedQuery.setParameter("image", image);
            typedQuery.setParameter("user", user);

            return typedQuery.getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}