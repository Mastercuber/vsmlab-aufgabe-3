package de.hska.vsmlab.microservice.core.user.web;

import de.hska.vsmlab.microservice.core.user.perstistence.dao.UserDao;
import de.hska.vsmlab.microservice.core.user.perstistence.model.User;
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

    public User getUser(final Long userId) {
        final Optional<User> byId = userDao.findById(userId);
        User user = null;
        if(byId.isPresent()) {
            user = byId.get();
        }

        return user;
    }

    @Override
    public User createUser(User user) {
        try {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            return userDao.save(user);
        } catch (Exception e) {
            return new User();
        }
    }

    @Override
    public User setActive(Long userId) {
        Optional<User> byId = userDao.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setActive(true);
            user = userDao.save(user);

            return user;
        }

        return null;
    }

    @Override
    public User setInactive(Long userId) {
        Optional<User> byId = userDao.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setActive(false);
            user = userDao.save(user);

            return user;
        }

        return null;
    }

    public User getUserByEmailCache(String email) {
        return userDao.findByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
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

    @Override
    public boolean deleteAllUsers() {
        userDao.deleteAll();

        return true;
    }
}

