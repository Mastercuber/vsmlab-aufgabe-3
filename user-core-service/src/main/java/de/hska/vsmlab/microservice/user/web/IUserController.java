package de.hska.vsmlab.microservice.user.web;

import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.springframework.web.bind.annotation.*;

public interface IUserController {

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    User getUser(@PathVariable final Long userId);

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    User createUser(@RequestParam("email") final String email, @RequestParam("password") final String password, @RequestParam("firstname") final String firstname, @RequestParam("lastname") final String lastname);

    @RequestMapping(value = "/user/{userId}/active", method = RequestMethod.PATCH)
    @ResponseBody
    Boolean setActive(@PathVariable final Long userId);

    @RequestMapping(value = "/user/{userId}/inactive", method = RequestMethod.PATCH)
    @ResponseBody
    Boolean setInactive(@PathVariable final Long userId);
}
