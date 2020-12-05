package de.hska.vsmlab.microservice.user.perstistence.dao;

import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
