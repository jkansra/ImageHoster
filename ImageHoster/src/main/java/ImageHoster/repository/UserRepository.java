package ImageHoster.repository;

import ImageHoster.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class UserRepository {
    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the User object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public boolean registerUser(User newUser) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            String upperCaseChars = "(.*[A-Z].*)";
            String lowerCaseChars = "(.*[a-z].*)";
            String numbers = "(.*[0-9].*)";
            String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
            String pwd = newUser.getPassword();
            if((pwd.matches(upperCaseChars) || pwd.matches(lowerCaseChars) &&
                    pwd.matches(numbers) && pwd.matches(specialChars)) ) {
                transaction.begin();
                //persist() method changes the state of the model object from transient state to persistence state
                em.persist(newUser);
                transaction.commit();
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e) {
            transaction.rollback();
        }
        return false;

    }


    //The method receives the entered username and password
    //Creates an instance of EntityManager
    //Executes JPQL query to fetch the user from User class where username is equal to received username and password is equal to received password
    //Returns the fetched user
    //Returns null in case of NoResultException
    public User checkUser(String username, String password) {
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<User> typedQuery = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            typedQuery.setParameter("username", username);
            typedQuery.setParameter("password", password);

            return typedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}