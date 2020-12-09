package de.hska.vsmlab.microservice.test;

import de.hska.vsmlab.microservice.composite.model.User;
import de.hska.vsmlab.microservice.composite.web.ILoginSystemController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ILoginSystemController.class)
class LoginSystemCompositeServiceTests {
    private TestRestTemplate client = new TestRestTemplate();
    private static User user;

    @BeforeEach
    public void setupEach() {
        client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8762"));
    }

    @BeforeAll
    public static void setup() {
        TestRestTemplate client = new TestRestTemplate();
        client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8759"));

        user = new User("tester@hs-karlsruhe.de", "Tester", "Testender", "test2", 1);
        User tmpUser = client.postForEntity("/user", user, User.class).getBody();
        user.setId(tmpUser.getId());
    }

    @AfterAll
    public static void teardown() {
        TestRestTemplate client = new TestRestTemplate();
        client.delete("http://localhost:8759/user");
    }

    @Test
    void loginUser() {
        ResponseEntity<User> response = client.postForEntity("/login", user, User.class);
        assertTrue(response.getBody().isActive());
    }

	@Test
	void logoutUser() {
        ResponseEntity<Void> response = client.getForEntity("/logout", void.class);
        assertEquals("OK", response.getStatusCode().getReasonPhrase());
    }

    @Test
    void registerNewUser() {
        User user = new User("tester-2@hs-karlsruhe.de", "Tester", "Testender", "test", 1);
        ResponseEntity<User> response = client.postForEntity("/user/register", user, User.class);
        user = response.getBody();
        assertEquals("Tester", user.getFirstname());
        assertEquals("Testender", user.getLastname());
        assertEquals("tester-2@hs-karlsruhe.de", user.getEmail());
        assertTrue(user.getId() > 0);
    }

    @Test
    void registerSameUserAgain() {
        ResponseEntity<User> userResponseEntity = client.postForEntity("/user/register", user, User.class);
        // when the user exists, then an empty object with default values is returned
        assertEquals(0, userResponseEntity.getBody().getId());
    }
}
