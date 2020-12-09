package de.hska.vsmlab.microservice.user.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.vsmlab.microservice.user.perstistence.dao.UserDao;
import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
public class UserController implements IUserController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private UserDao userDao;

    private LinkedHashMap<Long, User> usersCache = new LinkedHashMap<>();
    private LinkedHashMap<String, Long> emailToIdMappings = new LinkedHashMap<>();

    public User getUserByIdCache(final Long userId) {
        return usersCache.getOrDefault(userId, new User());
    }

    @Override
    @HystrixCommand(fallbackMethod = "getUserByIdCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public User getUser(final Long userId) {
        final Optional<User> byId = userDao.findById(userId);
        User user = null;
        if(byId.isPresent()) {
            user = byId.get();
            emailToIdMappings.putIfAbsent(user.getEmail(), user.getId());
            usersCache.putIfAbsent(user.getId(), user);
        }

        return user;
    }

    @Override
    public User createUser(String email, String password, String firstname, String lastname) {
        User user = new User();
        user.setEmail(email);
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

    public User getUserByEmailCache(String email) {
        final Long userId = emailToIdMappings.get(email);
        User user = null;

        if (userId != null) {
            user = usersCache.get(userId);
        }

        return user;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getUserByEmailCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public User getUserByEmail(String email) {
        final User user = userDao.findByEmail(email);

        if (user != null) {
            emailToIdMappings.putIfAbsent(email, user.getId());
            usersCache.putIfAbsent(user.getId(), user);
        }

        return user;
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

