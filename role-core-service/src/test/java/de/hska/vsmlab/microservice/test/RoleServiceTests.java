package de.hska.vsmlab.microservice.test;

import de.hska.vsmlab.microservice.role.perstistence.model.Role;
import de.hska.vsmlab.microservice.role.web.IRoleController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringBootTest(classes = IRoleController.class)
class RoleServiceTests {

    private TestRestTemplate client = new TestRestTemplate();
    private Role role;

    @BeforeEach
    public void setup() {
        Role admin = new Role("ADMIN", 1);
        client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8760"));

        role = client.postForEntity("/role", admin, Role.class).getBody();
    }

    @AfterEach
    public void teardown() {
        client.delete("/role/" + role.getId());
    }

	@Test
	void createRole() {
        Role testRole = new Role("TEST_ROLE", 1);
        ResponseEntity<Role> roleResponseEntity = client.postForEntity("/role", testRole, Role.class);
        testRole = roleResponseEntity.getBody();
        Assertions.assertTrue(testRole.getId() > 0);
	}

	@Test
    void deleteRole() {
        client.delete("/role/" + role.getId());
        Assertions.assertTrue(true);
    }

    @Test
    void receiveRole() {
        ResponseEntity<Role> role = client.getForEntity("/role/" + this.role.getId(), Role.class);
        Assertions.assertTrue(role.getBody().getId() > 0);
    }

}
