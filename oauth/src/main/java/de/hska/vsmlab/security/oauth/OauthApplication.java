package de.hska.vsmlab.security.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
//@EnableDiscoveryClient
public class OauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "Hello Oauth!";
	}
}
