package de.hska.vsmlab.microservice.composite.web;

import de.hska.vsmlab.microservice.composite.excpetions.UserExistsException;
import de.hska.vsmlab.microservice.composite.excpetions.UserNotFoundException;
import de.hska.vsmlab.microservice.composite.excpetions.WrongPasswordException;
import de.hska.vsmlab.microservice.composite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class LoginSystemController implements ILoginSystemController {

    @Autowired
    private IUserController userController;
    private long userId = -1;

    @Override
    public User login(final User user) throws UserNotFoundException, WrongPasswordException {
        final User userByUsername = userController.getUserByEmail(user.getEmail());
        if (userByUsername == null) {
            throw new UserNotFoundException();
        }
        // 1. Passwort stimmt überein?
        if (!BCrypt.checkpw(user.getPassword(), userByUsername.getPassword())) {
            throw new WrongPasswordException();
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
    public User register(User user) throws UserExistsException {
        User userByEmail = userController.getUserByEmail(user.getEmail());
        if (userByEmail != null) throw new UserExistsException();
        return userController.createUser(user);
    }
}
