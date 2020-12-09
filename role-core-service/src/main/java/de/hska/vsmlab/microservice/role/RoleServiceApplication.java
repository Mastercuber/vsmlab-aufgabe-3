package de.hska.vsmlab.microservice.role;

import de.hska.vsmlab.microservice.role.perstistence.dao.RoleDao;
import de.hska.vsmlab.microservice.role.perstistence.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class RoleServiceApplication {

    final Logger logger = LoggerFactory.getLogger(RoleServiceApplication.class);

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private RoleDao roleDao;


    public static void main(String[] args) {
        SpringApplication.run(RoleServiceApplication.class, args);
    }

    @PostConstruct
    public void generateTestData() {
        Role role = new Role();
        role.setLevel(1);
        role.setType("ADMIN");

        roleDao.save(role);

        logger.info("ROLE_ID: " + role.getId());
    }
}
