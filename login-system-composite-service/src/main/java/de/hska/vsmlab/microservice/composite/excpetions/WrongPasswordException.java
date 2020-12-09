package de.hska.vsmlab.microservice.composite.excpetions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Password is wrong");
    }
}
