package de.hska.vsmlab.microservice.role.perstistence.dao;

import de.hska.vsmlab.microservice.role.perstistence.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, Long> { }
