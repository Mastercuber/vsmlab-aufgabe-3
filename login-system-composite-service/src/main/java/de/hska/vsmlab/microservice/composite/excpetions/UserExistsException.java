package de.hska.vsmlab.microservice.composite.excpetions;

public class UserExistsException extends Throwable {
    public UserExistsException() {
        super("User exists");
    }
}
