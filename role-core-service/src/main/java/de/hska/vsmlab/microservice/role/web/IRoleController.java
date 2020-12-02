package de.hska.vsmlab.microservice.role.web;

import de.hska.vsmlab.microservice.role.perstistence.model.Role;
import org.springframework.web.bind.annotation.*;

public interface IRoleController {

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    Role getRole(@PathVariable final Long roleId);

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    @ResponseBody
    Role createRole(@RequestParam final String type, @RequestParam final Integer level);
}
