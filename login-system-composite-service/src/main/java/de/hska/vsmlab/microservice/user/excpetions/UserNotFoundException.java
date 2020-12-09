package de.hska.vsmlab.microservice.user.excpetions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found");
    }

}
