package de.hska.vsmlab.microservice.user.excpetions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Password is wrong");
    }
}
