package de.hska.vsmlab.microservice.role.web;

import de.hska.vsmlab.microservice.role.perstistence.model.Role;
import org.springframework.web.bind.annotation.*;

public interface IRoleController {

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    Role getRole(@PathVariable final long roleId);

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    @ResponseBody
    Role createRole(@RequestBody final Role role);

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
    Boolean deleteRole(@PathVariable final long roleId);
}
