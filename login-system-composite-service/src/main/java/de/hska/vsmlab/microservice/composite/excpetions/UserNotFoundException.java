package de.hska.vsmlab.microservice.composite.excpetions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found");
    }

}
