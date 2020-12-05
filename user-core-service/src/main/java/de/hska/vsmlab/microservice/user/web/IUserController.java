package de.hska.vsmlab.microservice.user.web;

import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.springframework.web.bind.annotation.*;

public interface IUserController {

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    User getUser(@PathVariable final Long userId);

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    User createUser(@RequestParam final String email, @RequestParam final String password, @RequestParam final String firstname, @RequestParam final String lastname);

    @RequestMapping(value = "/user/{userId}/active", method = RequestMethod.PATCH)
    @ResponseBody
    User setActive(@PathVariable final Long userId);

    @RequestMapping(value = "/user/{userId}/inactive", method = RequestMethod.PATCH)
    @ResponseBody
    User setInactive(@PathVariable final Long userId);

    @RequestMapping(value = "/user/byUsername", method = RequestMethod.GET)
    @ResponseBody
    User getUserByUsername(@RequestParam String username);

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    boolean deleteUser(@PathVariable Long userId);
}
