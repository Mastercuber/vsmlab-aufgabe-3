package de.hska.vsmlab.microservice.user.web;

import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class LoginSystemController implements ILoginSystemController {

    @Autowired
    private IUserController userController;
    private long userId = -1;

    @Override
    public User login(final User user) {
        final User userByUsername = userController.getUserByUsername(user.getUsername());
        if (userByUsername == null) {
            throw new Error("User not found");
        }
        // 1. Passwort stimmt überein?
        if (!BCrypt.checkpw(user.getPassword(), userByUsername.getPassword())) {
            throw new Error("Password does not match");
        }
        final User user2 = userController.setActive(userByUsername.getId());
        this.userId = user2.getId();
        // 2. Nutzer in Session
        return user2;
    }

    @Override
    public void logout() {
        if (this.userId != -1) {
            userController.setInactive(this.userId);
            this.userId = -1;
            return;
        }

        System.out.println("Already logged out");
    }

    @Override
    public User register(User user) {
        return userController.createUser(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname());
    }
}
