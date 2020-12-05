package de.hska.vsmlab.microservice.user.web;

import de.hska.vsmlab.microservice.user.perstistence.model.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("role-service")
public interface IRoleController {

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    Role getRole(@PathVariable final Long roleId);

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    @ResponseBody
    Role createRole(@RequestParam final String type, @RequestParam final Integer level);
}
