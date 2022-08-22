package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Specific User methods.
 */
public class UserDaoImpl extends AutoparkDaoImpl<User> {

    public UserDaoImpl() {
        setClazz(User.class);
    }

    public User findByUsername(final String username) {
        TypedQuery<User> query = entityManager
                .createQuery("from User u where u.username=:username", User.class)
                .setParameter("username", username);
        return getSingleResult(query);
    }

    public boolean isAuthenticated(final String username, final String password) {
        User user = findByUsername(username);
        if(user == null){
            throw new NoResultException();
        }
        return password.equals(user.getPassword());
    }
}
