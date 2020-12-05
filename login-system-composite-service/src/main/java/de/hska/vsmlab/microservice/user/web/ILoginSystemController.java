package de.hska.vsmlab.microservice.user.web;

import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.springframework.web.bind.annotation.*;

public interface ILoginSystemController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    User login(@RequestBody User user);

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    void logout();

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    User register(@RequestBody User user);
}
