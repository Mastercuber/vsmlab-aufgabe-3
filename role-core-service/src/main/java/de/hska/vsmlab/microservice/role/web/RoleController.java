package de.hska.vsmlab.microservice.role.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.vsmlab.microservice.role.perstistence.dao.RoleDao;
import de.hska.vsmlab.microservice.role.perstistence.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
public class RoleController implements IRoleController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    @Lazy
    private RoleDao roleDao;

    private LinkedHashMap<Long, Role> rolesCache = new LinkedHashMap<>();

    public Role getRoleByIdCache(final Long roleId) {
        return rolesCache.getOrDefault(roleId, new Role());
    }

    @Override
    @HystrixCommand(fallbackMethod = "getRoleByIdCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Role getRole(final Long roleId) {
        final Optional<Role> byId = roleDao.findById(roleId);

        return byId.isPresent() ? byId.get() : null;
    }

    @Override
    public Role createRole(String type, Integer level) {
        Role role = new Role();
        role.setType(type);
        role.setLevel(level);

        return roleDao.save(role);
    }
}

