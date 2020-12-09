package de.hska.vsmlab.microservice.composite.web;

import de.hska.vsmlab.microservice.composite.excpetions.UserExistsException;
import de.hska.vsmlab.microservice.composite.excpetions.UserNotFoundException;
import de.hska.vsmlab.microservice.composite.excpetions.WrongPasswordException;
import de.hska.vsmlab.microservice.composite.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ILoginSystemController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    User login(@RequestBody User user) throws UserNotFoundException, WrongPasswordException;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    void logout();

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    User register(@RequestBody User user) throws UserExistsException;
}
