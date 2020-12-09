package de.hska.vsmlab.microservice.test;

import de.hska.vsmlab.microservice.user.perstistence.model.User;
import de.hska.vsmlab.microservice.user.web.ILoginSystemController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ILoginSystemController.class)
class UserServiceTests {
    private static RestTemplate client = new RestTemplate();
    private static User user;
    private static User newUser;
    private static long newUserId = -1;

    @BeforeAll
    public static void setup() {
        client.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8762"));
        user = new User("armin@hs-karlsruhe.de", "", "", "test", 1);
        newUser = new User("tester@hs-karlsruhe.de", "Tester", "Testender", "test2", 1);
    }

    @AfterAll
    public static void teardown() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8759/user/" + newUserId);
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
        ResponseEntity<User> response = client.postForEntity("/user/register", newUser, User.class);
        User user = response.getBody();
        assertEquals("Tester", user.getFirstname());
        assertEquals("Testender", user.getLastname());
        assertEquals("tester@hs-karlsruhe.de", user.getEmail());
        newUserId = user.getId();
    }

    @Test
    void registerSameUserAgain() {
        Exception exception = assertThrows(HttpServerErrorException.InternalServerError.class, () -> {
           client.postForEntity("/user/register", newUser, User.class);
        });

        String expectedMessage = "500";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
