package facades;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import errorhandling.AuthenticationException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 * @author amanda, benjamin, amalie
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = getEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null) {
                throw new AuthenticationException("Invalid username! Please try again");
            }
            if(!user.verifyPassword(password))
            {
                //This updates the timestamp in the database
                this.updateFailedLogin(user);
                throw new AuthenticationException("Invalid password or you failed to login before! Please try again in 30 seconds");      
            }
        } finally {
            em.close();
        }
        return user;
    }

    public void createUser(String username, String password) throws AuthenticationException {

        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();

            TypedQuery<Role> query = em.createQuery("select r from Role r WHERE r.roleName =:role", Role.class);
            List<Role> userRole = query.setParameter("role", "user").getResultList();

            User user = em.find(User.class, username);
            if (user != null) {
                throw new AuthenticationException("Username is already in use");
            }
            
            if(password.length() < 8 || !password.matches(".*\\d+.*") || !password.matches(".*[A-Z].*")){
                throw new AuthenticationException("Password must contains at least 1 digit and 1 capital letter, and be at least 8 characters long");
            }
            
            if(badPasswords(password))
            {
               throw new AuthenticationException("Bad choice of password, please be more creative"); 
            }
            
            user = new User(username, password);
            user.setRoleList(userRole);
            userRole.get(0).getUserList().add(user);

            em.persist(user);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
    
    private boolean badPasswords(String password){
        
       List<String> badPasswords = Arrays.asList("1234","12345", "123456","123456789", "123456789", "test1","password", "whatever", "1234567", "111111", "abc123");
        for (String badPassword : badPasswords) {
            
            if(password.equals(badPassword)){
                return true;
            }
        }     
        return false;
    }

    private void updateFailedLogin(User failedUser) throws AuthenticationException {
       EntityManager em = getEntityManager();
       
        User user = em.find(User.class, failedUser.getUserName());
        if (user == null) {

            throw new AuthenticationException("No user was found");
        }

        try {
            em.getTransaction().begin();   
            user.setLastFailedLogin(failedUser.getFailedLogin());
            em.merge(user);
            em.persist(user);
            em.getTransaction().commit();

        } finally {
            em.close();
        }}

}
