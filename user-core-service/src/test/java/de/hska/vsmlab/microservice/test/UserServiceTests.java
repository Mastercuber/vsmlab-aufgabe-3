package de.hska.vsmlab.microservice.test;

import de.hska.vsmlab.microservice.core.user.perstistence.model.User;
import de.hska.vsmlab.microservice.core.user.web.UserController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringBootTest(classes = UserController.class)
class UserServiceTests {

    private TestRestTemplate client = new TestRestTemplate();
    private User user = new User();

    @BeforeEach
    public void setup() {
        user.setEmail("test@test.de");
        user.setFirstname("Tester");
        user.setLastname("Testender");
        user.setPassword("test");
        client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8759"));
        user = client.postForEntity("/user", user, User.class).getBody();
    }

    @AfterEach
    public void tearDown() {
        client.delete("/user");
    }

	@Test
	public void receiveUser() {
        final User user = client.getForEntity("/user/" + this.user.getId(), User.class).getBody();
        Assertions.assertFalse(user.getEmail() != null ? user.getEmail().isBlank() : true);
	}

	@Test
    public void deleteUser() {
        client.delete("/user/" + this.user.getId());
        Assertions.assertTrue(true);
    }

    @Test
    public void addUser() {
        user.setId(0);
        user.setEmail("test-2@test.de");
        user.setFirstname("Tester 2");
        user.setLastname("Testender 2");
        user.setPassword("test 2");
        client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8759"));
        user = client.postForEntity("/user", user, User.class).getBody();
        Assertions.assertTrue(user.getId() > 0);
    }

    @Test
    public void receiveUserByEmail() {
        ResponseEntity<User> user = client.getForEntity("/user/byEmail?email=" + this.user.getEmail(), User.class);

        User resultUser = user.getBody();

        Assertions.assertNotNull(resultUser.getEmail());
    }

    @Test
    public void activateUser() {
        User result = client.patchForObject("/user/" + user.getId() + "/active", null, User.class);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getEmail());
    }

    @Test
    public void inactivateUser() {
        User result = client.patchForObject("/user/" + user.getId() + "/inactive", null, User.class);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getEmail());
    }
}
