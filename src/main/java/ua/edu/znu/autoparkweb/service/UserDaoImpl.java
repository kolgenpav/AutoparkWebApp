package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.User;
import javax.persistence.Query;

/**
 * Specific User methods.
 */
public class UserDaoImpl extends AutoparkDaoImpl<User> {

    public User findByUsername(final String username) {
        Query query = entityManager.createQuery("from User u where u.username=:username")
                .setParameter("username", username);
        return (User) query.getSingleResult();
    }

    public boolean isAuthenticated(final String username, final String password) {
        User user = findByUsername(username);
        return password.equals(user.getPassword());
    }
}
