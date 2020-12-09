package de.hska.vsmlab.microservice.user;

import de.hska.vsmlab.microservice.user.perstistence.dao.UserDao;
import de.hska.vsmlab.microservice.user.perstistence.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class UserServiceApplication {

    final Logger logger = LoggerFactory.getLogger(UserServiceApplication.class);

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UserDao userDao;


    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @PostConstruct
    public void generateTestData() {
        User user = new User();
        user.setFirstname("Armin");
        user.setLastname("Kunkel");
        user.setUsername("armin@hs-karlsruhe.de");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setRoleId(1);

        userDao.save(user);

        logger.info("USER_ID: " + user.getId());
    }
}
