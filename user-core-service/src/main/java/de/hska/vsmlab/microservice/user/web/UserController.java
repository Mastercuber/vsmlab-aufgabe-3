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
    private LinkedHashMap<String, Long> userNameToIdMappings = new LinkedHashMap<>();

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
            userNameToIdMappings.putIfAbsent(user.getUsername(), user.getId());
            usersCache.putIfAbsent(user.getId(), user);
        }

        return user;
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

    public User getUserByUsernameCache(String username) {
        final Long userId = userNameToIdMappings.get(username);
        User user = null;

        if (userId != null) {
            user = usersCache.get(userId);
        }

        return user;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getUserByUsernameCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public User getUserByUsername(String username) {
        final User user = userDao.findByUsername(username);

        if (user != null) {
            userNameToIdMappings.putIfAbsent(username, user.getId());
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

