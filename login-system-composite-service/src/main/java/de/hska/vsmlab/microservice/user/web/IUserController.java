package de.hska.vsmlab.microservice.user.web;

import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("user-service")
public interface IUserController {

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    User getUser(@PathVariable final Long userId);

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    User createUser(@RequestParam final String email, @RequestParam final String password, @RequestParam final String firstname, @RequestParam final String lastname);

    @RequestMapping(value = "/user/{userId}/active", method = RequestMethod.PATCH)
    @ResponseBody
    User setActive(@RequestHeader(value="X-HTTP-Method-Override", defaultValue="PATCH") @PathVariable final Long userId);

    @RequestMapping(value = "/user/{userId}/inactive", method = RequestMethod.PATCH)
    @ResponseBody
    User setInactive(@PathVariable final Long userId);

    @RequestMapping(value = "/user/byEmail", method = RequestMethod.GET)
    @ResponseBody
    User getUserByEmail(@RequestParam String email);

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    boolean deleteUser(@PathVariable Long userId);
}
