package de.hska.vsmlab.microservice.core.user.web;

import de.hska.vsmlab.microservice.core.user.perstistence.model.User;
import org.springframework.web.bind.annotation.*;

public interface IUserController {

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    User getUser(@PathVariable final Long userId);

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    User createUser(@RequestBody User user);

    @RequestMapping(value = "/user/{userId}/active", method = RequestMethod.PATCH)
    @ResponseBody
    User setActive(@PathVariable final Long userId);

    @RequestMapping(value = "/user/{userId}/inactive", method = RequestMethod.PATCH)
    @ResponseBody
    User setInactive(@PathVariable final Long userId);

    @RequestMapping(value = "/user/byEmail", method = RequestMethod.GET)
    @ResponseBody
    User getUserByEmail(@RequestParam String email);

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    boolean deleteUser(@PathVariable Long userId);

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    @ResponseBody
    boolean deleteAllUsers();
}
