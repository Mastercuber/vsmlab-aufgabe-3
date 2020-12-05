package de.hska.vsmlab.microservice.user.web;

import de.hska.vsmlab.microservice.user.perstistence.dao.UserDao;
import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController implements IUserController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private UserDao userDao;

    @Override
    public User getUser(final Long userId) {
        final Optional<User> byId = userDao.findById(userId);

        return byId.isPresent() ? byId.get() : null;
    }

    @Override
    public User createUser(String username, String password, String firstname, String lastname) {
        User user = new User();
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setRoleId(1);

        return userDao.save(user);
    }

    @Override
    public User setActive(Long userId) {
        Optional<User> byId = userDao.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setActive(true);
            return userDao.save(user);
        }

        return null;
    }

    @Override
    public User setInactive(Long userId) {
        Optional<User> byId = userDao.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setActive(false);
            return userDao.save(user);
        }

        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean deleteUser(Long userId) {
        try {
            userDao.deleteById(userId);
            return true;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

